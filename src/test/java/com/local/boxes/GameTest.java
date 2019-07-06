package com.local.boxes;

import com.local.boxes.algorythm.BoxGameContext;
import com.local.boxes.algorythm.RewardFinder;
import com.local.boxes.factory.GameFactory;
import com.local.boxes.model.Box;
import com.local.boxes.model.SIGNS;
import com.local.boxes.shuffle.BasicShuffle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    private Game game;
    private Map<Integer, Integer> bonuses;
    private List<SIGNS> signs;
    private Map<Integer, Integer> secondChanceAward;
    private List<SIGNS> secondChanceNewLife;
    private Set<Integer> expectedAnswers;
    @Mock
    private Stack<Box> shuffled;
    @Mock
    private Stack<Box> secondChanceShuffled;


    @BeforeEach
    void setUp() throws IOException {
        Utils demoBuildHelper = new Utils();
        demoBuildHelper.basicSetup();
        bonuses = demoBuildHelper.getBonuses();
        signs = demoBuildHelper.getSigns();
        secondChanceAward = demoBuildHelper.getSecondChanceAward();
        secondChanceNewLife = demoBuildHelper.getSecondChanceNewLife();

        shuffled = new Utils().fillInMockedShuffledDeck(bonuses, signs);
        secondChanceShuffled = new Utils().fillInMockedShuffledDeck(secondChanceAward, secondChanceNewLife);

        game = new GameFactory().getGameInstance(
                bonuses, signs, secondChanceAward,
                secondChanceNewLife, new BasicShuffle(), false
        );

        expectedAnswers = Files.readAllLines(Paths.get("src/test/resources/expected_result.txt")).
                stream().map(e -> e.split(",")).
                flatMapToInt(e -> Arrays.stream(Stream.of(e).mapToInt(Integer::parseInt).toArray()))
                .boxed().collect(Collectors.toSet());
    }


    @Test
    void playRoundUseCasesTest() throws NoSuchFieldException {

        FieldSetter.setField(game, game.getClass().getDeclaredField("shuffled"), shuffled);
        secondChanceShuffled = reverse(secondChanceShuffled);
        FieldSetter.setField(game, game.getClass().getDeclaredField("secondChanceShuffled"), secondChanceShuffled);
        game.playRound(true);

        assertEquals(10, game.getResult());

        game.resetAndShuffle(new BasicShuffle());

        secondChanceShuffled = new Utils().fillInMockedShuffledDeck(secondChanceAward, secondChanceNewLife);
        FieldSetter.setField(game, game.getClass().getDeclaredField("secondChanceShuffled"), secondChanceShuffled);
        shuffled = new Utils().fillInMockedShuffledDeck(bonuses, signs);
        FieldSetter.setField(game, game.getClass().getDeclaredField("shuffled"), shuffled);
        game.playRound(true);

        assertEquals(0, game.getResult());

        game.resetAndShuffle(new BasicShuffle());

    }


    @Test
    void tenMSimulationWithAlgorithm() throws NoSuchFieldException {
        for (int i = 0; i < 10000000; i++) {
            BoxGameContext boxGameContext = new BoxGameContext(new RewardFinder(), game.getShuffled(), game.getSecondChanceShuffled());
            FieldSetter.setField(game, game.getClass().getDeclaredField("boxes"), game.getShuffled());
            game.playRound(true);

            assertTrue(expectedAnswers.contains(game.getResult()));

            boxGameContext.superBoxGameRun();

            assertEquals(boxGameContext.getResult(), game.getResult());

            game.resetAndShuffle(new BasicShuffle());
        }
    }


    @Test
    void algorithmScenarioTest() {
        BoxGameContext boxGameContext1 = new BoxGameContext(new RewardFinder(), shuffled, secondChanceShuffled);
        boxGameContext1.superBoxGameRun();
        assertEquals(0, boxGameContext1.getResult());
        Collections.reverse(shuffled);
        secondChanceShuffled = new Utils().fillInMockedShuffledDeck(secondChanceAward, secondChanceNewLife);
        Collections.reverse(secondChanceShuffled);
        BoxGameContext boxGameContext2 = new BoxGameContext(new RewardFinder(), shuffled, secondChanceShuffled);
        boxGameContext2.superBoxGameRun();
        assertEquals(175, boxGameContext2.getResult());

    }


    private Stack<Box> reverse(Stack<Box> boxes) {
        Stack<Box> stack = new Stack<>();
        for (int i = 0; i < boxes.size(); i++) {
            stack.push(boxes.pop());
            i--;
        }
        return stack;
    }
}