package me.lotiny.libs.elo;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EloUtil {

    private final KFactor[] K_FACTORS = {
            new KFactor(0, 1000, 40),
            new KFactor(1001, 1400, 45),
            new KFactor(1401, 1800, 35),
            new KFactor(1801, 2200, 35)
    };

    private final int DEFAULT_K_FACTOR = 25;
    private final int WIN = 1;
    private final int LOSS = 0;

    /**
     * Calculate the new rating for a player after a match.
     *
     * @param rating         The player's current rating.
     * @param opponentRating The opponent's rating.
     * @param won            A boolean indicating whether the player won the match (true) or lost (false).
     * @return The new rating for the player.
     */
    public int getNewRating(int rating, int opponentRating, boolean won) {
        if (won) {
            // If the player won, calculate the new rating using the win score.
            return EloUtil.getNewRating(rating, opponentRating, EloUtil.WIN);
        } else {
            // If the player lost, calculate the new rating using the loss score.
            return EloUtil.getNewRating(rating, opponentRating, EloUtil.LOSS);
        }
    }

    /**
     * Calculate the new rating for a player after a match.
     *
     * @param rating         The player's current rating.
     * @param opponentRating The opponent's rating.
     * @param score          The score representing the outcome of the match (1 for win, 0 for loss, etc.).
     * @return The new rating for the player.
     */
    public int getNewRating(int rating, int opponentRating, int score) {
        double kFactor = EloUtil.getKFactor(rating); // Get the appropriate K-factor based on the player's rating.

        double expectedScore = EloUtil.getExpectedScore(rating, opponentRating, score); // Calculate the expected score.
        int newRating = EloUtil.calculateNewRating(rating, score, expectedScore, kFactor);

        // Check if the player won and their rating didn't change. If so, increase their rating by 1.
        if (score == 1 && newRating == rating) {
            newRating++;
        }

        return newRating;
    }

    /**
     * Calculate the new rating for a player based on the Elo formula.
     *
     * @param oldRating     The player's current rating.
     * @param score         The score representing the outcome of the match (1 for win, 0 for loss, etc.).
     * @param expectedScore The expected score calculated using the Elo formula.
     * @param kFactor       The K-factor used in Elo rating calculations.
     * @return The new rating for the player.
     */
    private int calculateNewRating(int oldRating, int score, double expectedScore, double kFactor) {
        return oldRating + (int) (kFactor * (score - expectedScore));
    }

    /**
     * Get the K-factor (weight) to use based on a player's rating.
     *
     * @param rating The player's rating.
     * @return The appropriate K-factor value.
     */
    private double getKFactor(int rating) {
        for (int i = 0; i < EloUtil.K_FACTORS.length; i++) {
            if (rating >= EloUtil.K_FACTORS[i].getStartIndex() && rating <= EloUtil.K_FACTORS[i].getEndIndex()) {
                return EloUtil.K_FACTORS[i].getValue();
            }
        }

        // If no matching range is found, return the default K-factor.
        return EloUtil.DEFAULT_K_FACTOR;
    }

    /**
     * Calculate the expected score of a player in a match against an opponent.
     *
     * @param rating         The player's rating.
     * @param opponentRating The opponent's rating.
     * @param score          The score representing the outcome of the match (1 for win, 0 for loss, etc.).
     * @return The expected score based on the Elo formula.
     */
    private double getExpectedScore(int rating, int opponentRating, int score) {
        return 1 / (1 + Math.pow(10, ((double) (opponentRating - rating) / 300)));
    }
}
