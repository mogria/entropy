package org.playentropy.circuit;

import java.util.Collection;
import org.springframework.data.annotation.PersistenceConstructor;

public class PowerSourcePiece extends AbstractPiece {
    private Energy outputEnergy;

    @PersistenceConstructor
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

