package org.playentropy.circuitboard;

import java.util.Collection;

public class PowerSourcePiece extends AbstractPiece {
    private Energy outputEnergy;

    public PowerSourcePiece(Energy outputEnergy) {
        super(new Vector(2, 1));

        this.outputEnergy = outputEnergy;

        final Vector offset = new Vector(1, 0);
        addConnector(offset, new Vector(1, 0));
    }

    @Override
    public void update(Collection<Connector> inputConnectors,
                       Collection<Connector> outputConnectors) {
        outputConnectors.stream().forEach(
            connector -> connector.writeEnergy(outputEnergy)
        );
    }

    @Override
    public String toString() {
        return "@";
    }
}

