import java.util.*;
import java.util.stream.Collectors;

public class TrickShot {

	public static void bruteForce() {

		int MIN_X = 20, MAX_X = 30;
		int MIN_Y = -10, MAX_Y= -5;
		// int MIN_X = 79, MAX_X = 137;
		// int MIN_Y = -176, MAX_Y= -117;

		List<Integer> maxYForTrajectoriesCrossingTargetArea = new ArrayList<>();
		List<String> startingVelocitiesHittingTargetArea = new ArrayList<>();

		for (int x = 0; x < 500; x++) {
			for (int y = -500; y < 500; y++) {

				// init variables for this round
				int localX = x, localY = y;
				int dX = x, dY = y;
				int maxForThisTrajectory = Integer.MIN_VALUE;

				// run while hasn't passed the target area yet
				while (localX <= MAX_X || localY >= MIN_Y) {

					if (localY > maxForThisTrajectory)
						maxForThisTrajectory = localY;

					boolean reachedTargetArea = localX >= MIN_X && localX <= MAX_X
						&& localY >= MIN_Y && localY <= MAX_Y;
					if (reachedTargetArea) {
						maxYForTrajectoriesCrossingTargetArea.add(maxForThisTrajectory);
						startingVelocitiesHittingTargetArea.add(x + "," + y);
						break;
					}

					// dX changes until dX is zero
					if (dX != 0) --dX;
					localX += dX;

					// dY get's decremented each step
					localY += --dY;

					// also stop if the trajectory would never reach the 
					// target area
					if (dY < MIN_Y) break;

				}

			}
		}

		System.out.println("Max y: " + Collections.max(maxYForTrajectoriesCrossingTargetArea));
		System.out.println("Distinct initial velocities: " + startingVelocitiesHittingTargetArea.size());

	}


	public static void main(String[] args) {
		bruteForce();
	}
}
