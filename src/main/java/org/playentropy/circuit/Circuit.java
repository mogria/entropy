package org.playentropy.circuit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class Circuit {

    private Board boardManager;
    private Map<Connector, Connector> connectorMapping  = null;

    public Circuit(Board boardManager) {
        this.boardManager = boardManager;
        generateConnectorMapping();
    }

    private Optional<Connector> getReverseConnector(Piece piece1,
                                                              Connector connector1) {
        Piece piece2 = getConnectedPiece(piece1, connector1);
        return  piece2.getConnectors()
                    .stream()
                    .filter(
                        connector2 ->
                            getConnectedPiece(piece2, connector2) == piece1)
                    .findFirst();
    }

    private Piece getConnectedPiece(Piece piece,
                                     Connector connector) {
        assert piece != null;
        assert connector != null;
        Vector position = boardManager.getPiecePosition(piece)
                              .add(connector.getConnectionOffset());
        return boardManager.getPieceAt(position);
    }

    public void generateConnectorMapping() {
        connectorMapping = new HashMap<Connector, Connector>();

        boardManager.getPiecesList().stream()
            .flatMap(piece -> piece.getConnectors().stream())
            .forEach(connector -> connector.setConnected(false));

        boardManager.getPiecesList().stream()
            .forEach(piece1 ->
                piece1.getConnectors()
                    .stream()
                    .forEach(connector1 -> {
                        getReverseConnector(piece1, connector1)
                            .ifPresent(connector2 -> {
                                connector1.setConnected(true);
                                connector2.setConnected(true);
                                connectorMapping.put(connector1, connector2);
                            });
                    })
            );
    }

    public void runCircuitTick() {
        // let every piece write to it's output connector
        boardManager.getPiecesList().stream()
            .forEach(piece -> piece.update());

        // propagate the output in the connectors further
        List<Connector> updatedConnectors =
            connectorMapping.keySet()
                .stream()
                .filter(connector1 -> connector1.isUpdated())
                .collect(Collectors.toList());

        updatedConnectors
            .stream()
            .forEach(connector1 -> {
                Connector connector2 =
                    connectorMapping.get(connector1);
                connector2.writeEnergy(connector1.readEnergy());
            });
        
    }


    public Map<Connector, Connector> getConnectorMapping() {
        return connectorMapping;
    }

    public Board getBoard() {
        return boardManager;
    }
}
