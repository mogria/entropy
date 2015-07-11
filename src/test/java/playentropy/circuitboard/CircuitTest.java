package org.playentropy.test.circuitboard;

import org.playentropy.circuitboard.Vector;
import org.playentropy.circuitboard.Energy;
import org.playentropy.circuitboard.Piece;
import org.playentropy.circuitboard.AbstractPiece;
import org.playentropy.circuitboard.WirePiece;
import org.playentropy.circuitboard.PowerSourcePiece;
import org.playentropy.circuitboard.Connector;
import org.playentropy.circuitboard.Board;
import org.playentropy.circuitboard.Board.NoSpaceException;
import org.playentropy.circuitboard.Board.PieceAlreadyInUseException;
import org.playentropy.circuitboard.Circuit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class CircuitTest {
    static int SIZE_X = 6;
    static int SIZE_Y= 7;
    private Board board;
    private Circuit circuit;

    private PowerSourcePiece powerSource;

    private MockConsumerPiece consumer1;
    private MockConsumerPiece consumer2;
    private MockConsumerPiece consumer3;
    private MockConsumerPiece consumer4;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public class MockConsumerPiece extends AbstractPiece {
        private Energy currentEnergy = new Energy(0, 0);

        private String display;
        public MockConsumerPiece(String display) {
            super(new Vector(1, 1));
            this.display = display;
            addConnector(new Vector(0, 0), new Vector(0, 1));
        }

        @Override
        public void update(Collection<Connector> inputConnectors,
                           Collection<Connector> outputConnectors) {
            currentEnergy = new Energy(0, 0);
            inputConnectors.stream().forEach(connector -> {
                Energy current = connector.readEnergy();
                currentEnergy = currentEnergy.merge(current);
            });
        }

        @Override
        public String toString() {
            return display;
        }

        public Energy getCurrentEnergy() {
            return currentEnergy;
        }
    }

    @Before
    public void setUp()
        throws Board.NoSpaceException,
               Board.PieceAlreadyInUseException {
        board = new Board(SIZE_X, SIZE_Y);

        powerSource = new PowerSourcePiece(new Energy(100, 100)); // 1
        board.placePiece(powerSource, 0, SIZE_Y - 1);
        board.placePiece(new WirePiece(), 2, 6); // 2
        board.placePiece(new WirePiece(), 2, 5); // 2
        board.placePiece(new WirePiece(), 2, 4); // 3
        board.placePiece(new WirePiece(), 2, 3); // 2
        board.placePiece(new WirePiece(), 2, 2); // 2
        board.placePiece(new WirePiece(), 3, 2); // 2

        board.placePiece(new WirePiece(), 3, 4); // 2
        board.placePiece(new WirePiece(), 4, 4); // 2
        board.placePiece(new WirePiece(), 4, 3); // 2
        board.placePiece(new WirePiece(), 4, 2); // 3

        board.placePiece(new WirePiece(), 5, 2); // 1

        board.placePiece(new WirePiece(), 3, 6); // 1
        
        consumer1 = new MockConsumerPiece("1");
        board.placePiece(consumer1, 3, 5);

        consumer2 = new MockConsumerPiece("2");
        board.placePiece(consumer2, 2, 1);

        consumer3 = new MockConsumerPiece("3");
        board.placePiece(consumer3, 3, 3);

        consumer4 = new MockConsumerPiece("4");
        board.placePiece(consumer4, 5, 1);

        circuit = new Circuit(board);
    }

    @After
    public void tearDown() {
        circuit = null;
        board = null;
        consumer1 = null;
        consumer2 = null;
        consumer3 = null;
        consumer4 = null;
    }

   @Test
    public void testGetConnectorMapping() {
        Map<Connector, Connector> mapping =
            circuit.getConnectorMapping();

        mapping.keySet().stream().forEach(connector -> {
            Assert.assertTrue(mapping.containsKey(mapping.get(connector)));
            Assert.assertEquals(connector, mapping.get(mapping.get(connector)));
        });

    }

    @Test
    public void testRunCircuitTick() {

        System.out.println(circuit.getBoard().toString());

        for(int i = 0; i < 3; i++) {
            circuit.runCircuitTick();
            Assert.assertTrue(consumer1.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
            Assert.assertTrue(consumer2.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
            Assert.assertTrue(consumer3.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
            Assert.assertTrue(consumer4.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
        }

        for(int i = 0; i < 2; i++) {
            circuit.runCircuitTick();
            Assert.assertTrue(consumer1.getCurrentEnergy().getEnergyVector().equals(new Vector(50, 50)));
            Assert.assertTrue(consumer2.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
            Assert.assertTrue(consumer3.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
            Assert.assertTrue(consumer4.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
        }

        for(int i = 0; i < 1; i++) {
            circuit.runCircuitTick();
            Assert.assertTrue(consumer1.getCurrentEnergy().getEnergyVector().equals(new Vector(50, 50)));
            Assert.assertTrue(consumer2.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
            Assert.assertTrue(consumer3.getCurrentEnergy().getEnergyVector().equals(new Vector(12, 12)));
            Assert.assertTrue(consumer4.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
        }

        for(int i = 0; i < 3; i++) {
            circuit.runCircuitTick();
            Assert.assertTrue(consumer1.getCurrentEnergy().getEnergyVector().equals(new Vector(50, 50)));
            Assert.assertTrue(consumer2.getCurrentEnergy().getEnergyVector().equals(new Vector(12, 12)));
            Assert.assertTrue(consumer3.getCurrentEnergy().getEnergyVector().equals(new Vector(12, 12)));
            Assert.assertTrue(consumer4.getCurrentEnergy().getEnergyVector().equals(new Vector(0, 0)));
        }

        for(int i = 0; i < 100; i++) {
            circuit.runCircuitTick();
            Assert.assertTrue(consumer1.getCurrentEnergy().getEnergyVector().equals(new Vector(50, 50)));
            Assert.assertTrue(consumer2.getCurrentEnergy().getEnergyVector().equals(new Vector(12, 12)));
            Assert.assertTrue(consumer3.getCurrentEnergy().getEnergyVector().equals(new Vector(12, 12)));
            Assert.assertTrue(consumer4.getCurrentEnergy().getEnergyVector().equals(new Vector(24, 24)));
        }

    }
}
