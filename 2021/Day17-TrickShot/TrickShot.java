public class TrickShot {

	/* 
		Each step:
			x + x + (x > 0 ? -1 : 1)
			y + y - 1


		TEST: target area: x=20..30, y=-10..-5	
		ANSWER: target area: x=79..137, y=-176..-117
	*/



	public static void bruteForce() {

		// int MIN_X = 20, MAX_X = 30;
		// int MIN_Y = -10, MAX_Y= -5;
		int MIN_X = 79, MAX_X = 137;
		int MIN_Y = -176, MAX_Y= -117;

		for (int x = 0; x < 500; x++) {
			for (int y = -500; y < 500; y++) {

				// int x = 8;
				// int y = 0;

				int X = x;
				int Y = y;
				int MAX = Integer.MIN_VALUE;

				// if (X >= MIN_X && X <= MAX_X && Y >= MIN_Y && Y <= MAX_Y) {
				// 	System.out.println("x: " + x + "," + y + " MAX Y: " + MAX);
				// 	// System.out.println("x: " + x + "," + y);
				// }


				int DELTA_Y = Y;
				int DELTA_X = X;
				while (X <= MAX_X || Y >= MIN_Y) {
				// while (X <= MAX_X) {

					if (X >= MIN_X && X <= MAX_X && Y >= MIN_Y && Y <= MAX_Y) {
						System.out.println("x: " + x + "," + y + " MAX Y: " + MAX);
						// System.out.println("x: " + x + "," + y);
						break;
					}

					if (DELTA_X != 0) {
						--DELTA_X;
					}
					X += DELTA_X;

					// int DELTA_X = 0;
					// if (X != 0) {
					// 	DELTA_X = (X > 0) ? --X : ++X;
					// }
					// X += DELTA_X;					
					
					--DELTA_Y;
					Y += DELTA_Y;

					// System.out.println(X + "," + Y + " " + DELTA_Y);

					if (Y > MAX) MAX = Y;

					if (DELTA_Y < MIN_Y) break;
					// if (Y > MAX_Y) break;

					// if (Y > MAX_Y)
					// 	break;

				}

			}
		}

	}


	public static void main(String[] args) {
		bruteForce();
	}
}
