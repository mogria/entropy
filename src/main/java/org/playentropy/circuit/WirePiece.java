package org.playentropy.circuit;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class WirePiece extends AbstractPiece {
    public WirePiece() {
        super(new Vector(1, 1));

        final Vector offset = new Vector(0, 0);
        addConnector(offset, new Vector(0, -1));
        addConnector(offset, new Vector(1, 0));
        addConnector(offset, new Vector(0, 1));
        addConnector(offset, new Vector(-1, 0));
    }

    @Override
    public void update(Collection<Connector> inputConnectors,
                       Collection<Connector> outputConnectors) {
        // don't write any output if there is no input
        if(inputConnectors.size() == 0) return;

        Energy inputEnergy = inputConnectors
            .stream()
            .map(connector -> connector.readEnergy())
            .reduce(new Energy(0, 0), (a, b) -> a.merge(b));

        int numOutputs = outputConnectors.size();
        if(numOutputs <= 0) return;

        Energy outputEnergy = inputEnergy.split(numOutputs);
        outputConnectors.stream().forEach(
            connector -> connector.writeEnergy(outputEnergy)
        );
    }

    @Override
    public String toString() {
        Collection<Vector> connected =
            getConnectors().stream()
                .filter(connector -> connector.isConnected())
                .map(connector -> connector.getDirection())
                .collect(Collectors.toList());

        Optional<Vector> first = connected.stream().findFirst();

        Vector acc = connected.stream()
            .reduce(new Vector(0, 0), (x, y) -> x.add(y));
        if(connected.size() == 2) {
            if(first.isPresent() && acc.equals(new Vector(0, 0))) {
                return first.get().getX() != 0 ? "-" : "|";
            }
        } else if(connected.size() == 3) {
            if(acc.getX() == 0) {
                if(acc.getY() == 1) {
                    return "v";
                } else {
                    return "^";
                }
            } else {
                if(acc.getY() == 1) {
                    return "<";
                } else {
                    return ">";
                }
            }
        }
        return "+";
    }
}
