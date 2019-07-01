package com.local.boxes;

import com.local.boxes.factory.GameFactory;
import com.local.boxes.model.Box;
import com.local.boxes.model.SIGNS;
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

import static com.local.boxes.model.SIGNS.GO_GO_GO;
import static org.junit.jupiter.api.Assertions.*;


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
        bonuses = new HashMap<Integer, Integer>() {{
            put(100, 1);
            put(20, 2);
            put(5, 5);
        }};
        signs = new ArrayList<SIGNS>() {{
            add(SIGNS.EXTRA_LIFE);
            add(SIGNS.GAME_OVER);
            add(SIGNS.GAME_OVER);
            add(SIGNS.GAME_OVER);
        }};

        secondChanceAward = new HashMap<Integer, Integer>() {{
            put(5, 1);
            put(10, 1);
            put(20, 1);
        }};
        secondChanceNewLife = new ArrayList<SIGNS>() {{
            add(SIGNS.EXTRA_LIFE);
        }};
        shuffled = fillInMockedShuffledDeck(bonuses, signs);
        secondChanceShuffled = fillInMockedShuffledDeck(secondChanceAward, secondChanceNewLife);
        game = new GameFactory().getGameInstance(bonuses, signs, secondChanceAward, secondChanceNewLife);
        expectedAnswers = Files.readAllLines(Paths.get("src/test/resources/expected_result.txt")).
                stream().map(e -> e.split(",")).
                flatMapToInt(e -> Arrays.stream(Stream.of(e).mapToInt(Integer::parseInt).toArray()))
                .boxed().collect(Collectors.toSet());


    }

    private Stack<Box> fillInMockedShuffledDeck(Map<Integer, Integer> bonuses, List<SIGNS> signs) {
        Stack<Box> shuffled = new Stack<>();
        for (Integer bonus : bonuses.keySet()) {
            for (int i = 0; i < bonuses.get(bonus); i++) {
                shuffled.add(new Box().createBox(bonus, GO_GO_GO));
            }
        }
        for (SIGNS sign : signs) {
            shuffled.add(new Box().createBox(0, sign));
        }
        return shuffled;
    }

    @Test
    void playRoundUseCasesTest() throws NoSuchFieldException {
        FieldSetter.setField(game, game.getClass().getDeclaredField("shuffled"), shuffled);
        secondChanceShuffled = reverse(secondChanceShuffled);
        FieldSetter.setField(game, game.getClass().getDeclaredField("secondChanceShuffled"), secondChanceShuffled);
        game.playRound(true);
        assertEquals(20, game.getResult());
        game.resetAndShuffle();
        secondChanceShuffled = fillInMockedShuffledDeck(secondChanceAward, secondChanceNewLife);
        FieldSetter.setField(game, game.getClass().getDeclaredField("secondChanceShuffled"), secondChanceShuffled);
        shuffled = fillInMockedShuffledDeck(bonuses, signs);
        FieldSetter.setField(game, game.getClass().getDeclaredField("shuffled"), shuffled);
        game.playRound(true);
        assertEquals(0, game.getResult());
        game.resetAndShuffle();
        secondChanceShuffled = fillInMockedShuffledDeck(secondChanceAward, secondChanceNewLife);
        FieldSetter.setField(game, game.getClass().getDeclaredField("secondChanceShuffled"), secondChanceShuffled);
        shuffled = fillInMockedShuffledDeck(bonuses, signs);
        FieldSetter.setField(game, game.getClass().getDeclaredField("shuffled"), shuffled);
        game.playRound(true);
    }


    @Test
    void playManyRoundsTest() {
        for (int i = 0; i < 10000000; i++) {
            game.playRound(false);
            assertTrue(expectedAnswers.contains(game.getResult()));
            game.resetAndShuffle();
        }
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