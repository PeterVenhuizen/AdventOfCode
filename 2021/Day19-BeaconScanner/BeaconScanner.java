import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class BeaconScanner {

	record Point(int x, int y, int z) {
		@Override
		public String toString() {
			return String.format("%d,%d,%d", this.x, this.y, this.z);
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) return true;
			if (!(o instanceof Point)) return false;
			Point b = (Point) o;
			return this.x == b.x 
				&& this.y == b.y 
				&& this.z == b.z;
		}
	}
	
	record Scanner(String name, List<Point> beacons) {}

	private static List<Scanner> parseBeacons(String filename) throws IOException {
		List<Scanner> scanners = new ArrayList<>();
		
		List<String> lines = Files.lines(Paths.get(filename))
			.collect(Collectors.toList());

		String scannerName = "";
		List<Point> beacons = new ArrayList<>();
		for (String line : lines) {
			if (!line.isEmpty()) {

				if (line.substring(0, 3).equals("---")) {
					scannerName = line.replaceAll("-", "").trim();
				}

				else {
					Point point = new Point(
						Integer.parseInt(line.split(",")[0]),
						Integer.parseInt(line.split(",")[1]),
						Integer.parseInt(line.split(",")[2])
					);
					beacons.add(point);
				}
			}

			else {
				scanners.add(new Scanner(scannerName, beacons));
				scannerName = "";
				beacons = new ArrayList<>();
			}
		}

		scanners.add(new Scanner(scannerName, beacons));
		return scanners;
	}

	private static Point rotate(Point point, int i) {
		if (i == 0) { return new Point(point.x(), point.y(), point.z()); } // x, y, z
		else if (i == 1) { return new Point(point.x(), point.z(), -point.y()); } // x, z, -y
		else if (i == 2) { return new Point(point.x(), -point.y(), -point.z()); } // x, -y, -z
		else if (i == 3) { return new Point(point.x(), -point.z(), point.y()); } // x, -z, y
		else if (i == 4) { return new Point(point.y(), point.x(), -point.z()); } // y, x, -z
		else if (i == 5) { return new Point(point.y(), -point.z(), -point.x()); } // y, -z, -x
		else if (i == 6) { return new Point(point.y(), -point.x(), point.z()); } // y, -x, z
		else if (i == 7) { return new Point(point.y(), point.z(), point.x()); } // y, z, x
		else if (i == 8) { return new Point(point.z(), point.x(), point.y()); } // z, x, y
		else if (i == 9) { return new Point(point.z(), point.y(), -point.x()); } // z, y, -x
		else if (i == 10) { return new Point(point.z(), -point.x(), -point.y()); } // z, -x, -y
		else if (i == 11) { return new Point(point.z(), -point.y(), point.x()); } // z, -y, x
		else if (i == 12) { return new Point(-point.z(), point.x(), -point.y()); } // -z, x, -y
		else if (i == 13) { return new Point(-point.z(), -point.y(), -point.x()); } // -z, -y, -x
		else if (i == 14) { return new Point(-point.z(), -point.x(), point.y()); } // -z, -x, y
		else if (i == 15) { return new Point(-point.z(), point.y(), point.x()); } // -z, y, x
		else if (i == 16) { return new Point(-point.y(), point.x(), point.z()); } // -y, x, z
		else if (i == 17) { return new Point(-point.y(), point.z(), -point.x()); } // -y, z, -x
		else if (i == 18) { return new Point(-point.y(), -point.x(), -point.z()); } // -y, -x, -z
		else if (i == 19) { return new Point(-point.y(), -point.z(), point.x()); } // -y, -z, x
		else if (i == 20) { return new Point(-point.x(), point.y(), -point.z()); } // -x, y, -z
		else if (i == 21) { return new Point(-point.x(), -point.z(), -point.y()); } // -x, -z, -y
		else if (i == 22) { return new Point(-point.x(), -point.y(), point.z()); } // -x, -y, z
		else { return new Point(-point.x(), point.z(), point.y()); } // -x, z, y
	}

	private static void goingInsane(List<Point> beaconsOfScanner0, List<Scanner> scanners) {

		Map<String, Point> resolved = new HashMap<>();

		jump_back_here: while (scanners.size() > 0) {

			for (Scanner sc : scanners) {

				for (int i = 0; i < 24; i++) {

					// rotate all points
					List<Point> rotated = new ArrayList<>();
					for (Point p : sc.beacons())
						rotated.add(rotate(p, i));

					// compare with all points of scanner0
					Map<Point, Integer> distances = new HashMap<>();
					for (Point p0 : beaconsOfScanner0) {
						for (Point r : rotated) {
							Point diff = new Point(p0.x() - r.x(), p0.y() - r.y(), p0.z() - r.z());
							distances.merge(diff, 1, Integer::sum);
						}
					}
					
					// see if there is a distance that appears twelve or more times
					List<Point> twelveOrMore = distances.keySet().stream()
						.filter(k -> distances.get(k) >= 12)
						.toList();

					// only continue if one vector has twelve or more matches, otherwise
					// we have some funny business going on
					if (twelveOrMore.size() == 1) {

						Point trans = twelveOrMore.get(0);
						// System.out.println(sc.name() + " --> " + trans);

						// store the relative location of this scanner to scanner0
						resolved.put(sc.name(), trans);

						// add new beacons to scanner0, so they all are in one place
						for (Point r : rotated) {
							Point transformed = new Point(r.x() + trans.x(), 
								r.y() + trans.y(), r.z() + trans.z());

							if (!beaconsOfScanner0.contains(transformed)) {
								beaconsOfScanner0.add(transformed);
							}
						}

						// done with this scanner
						scanners.remove(sc);
						continue jump_back_here;
					}

				}
			}
		}

		System.out.println("Number of beacons: " + beaconsOfScanner0.size());

		int max = 0;
		for (Point a : resolved.values()) {
			for (Point b : resolved.values()) {
				int distance = Math.abs(b.x() - a.x()) +
								Math.abs(b.y() - a.y()) +
								Math.abs(b.z() - a.z());
				if (distance > max)
					max = distance;
			}
		}
		System.out.println("Largest Manhatten distance: " + max);

	}


	public static void main(String[] args) {
		try {
			List<Scanner> scanners = parseBeacons("input.txt");
			Scanner scanner0 = scanners.remove(0);
			goingInsane(scanner0.beacons(), scanners);
		} catch (IOException e) {}

	}
}
