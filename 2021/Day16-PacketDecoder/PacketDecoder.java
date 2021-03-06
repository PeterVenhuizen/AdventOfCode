import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

record Resolved(long size, long value) {}

public class PacketDecoder {
	public static long VERSION_SUM = 0L;

	public static Map<String, String> HEX2BIN = new HashMap<>() {{
		put("0", "0000");
		put("1", "0001");
		put("2", "0010");
		put("3", "0011");
		put("4", "0100");
		put("5", "0101");
		put("6", "0110");
		put("7", "0111");
		put("8", "1000");
		put("9", "1001");
		put("A", "1010");
		put("B", "1011");
		put("C", "1100");
		put("D", "1101");
		put("E", "1110");
		put("F", "1111");
	}};

	public static String convertToBinary(String input) {
		return input.chars()
			// .mapToObj(c -> Integer.toString(c))
			.mapToObj(c -> (char)c)
			.map(k -> HEX2BIN.get(Character.toString(k)))
			.collect(Collectors.joining(""));
	}

	public static long binaryToDecimal(String binary) {
		return Long.parseLong(binary, 2);
	}

	public static long doMath(Long packetType, List<Long> values) {

		if (packetType.equals(0L)) { // sum
			return values.stream().reduce(0L, Long::sum);
		} 

		else if (packetType.equals(1L)) { // product
			return values.stream().reduce(1L, (a, b) -> a * b);
		}

		else if (packetType.equals(2L)) { // minimum
			return values.stream().reduce(Long.MAX_VALUE, (a, b) -> Long.min(a, b));
		}

		else if (packetType.equals(3L)) { // maximum
			return values.stream().reduce(0L, (a, b) -> Long.max(a, b));
		}

		else if (packetType.equals(5L)) { // greater than
			return values.get(0) > values.get(1) ? 1 : 0;
		}

		else if (packetType.equals(6L)) { // greater than
			return values.get(0) < values.get(1) ? 1 : 0;
		}

		else { // greater than
			return values.get(0).equals(values.get(1)) ? 1 : 0;
		}
	}

	/* 
		Helper class to keep track of the position on the bits string
		and return (via take(n)) the first n bits of the string and 
		update pos
	*/
	static class Taker {
		private String bits;
		private int pos;
		private long version;

		public Taker(String bits) {
			this.bits = bits;
			this.pos = 0;
			this.version = 0L;
		}

		public int pos() {
			return this.pos;
		}

		public String take(int n) {
			String b = this.bits.substring(this.pos, this.pos + n);
			this.pos += n;
			return b;
		}

		public void addToVersion(long n) {
			this.version += n;
		}

		public long version() {
			return this.version;
		}
	}

	public static long decode(Taker bits) {
		long version = binaryToDecimal(bits.take(3));
		Long packetType = binaryToDecimal(bits.take(3));
		bits.addToVersion(version);

		boolean isALiteralPacket = packetType.equals(4L);
		if (isALiteralPacket) {
			String literal = "";
			while (true) {
				String firstBit = bits.take(1);
				literal += bits.take(4);
				if (firstBit.equals("0")) 
					return binaryToDecimal(literal);
			}

		} 

		List<Long> results = new ArrayList<>();

		String lengthTypeID = bits.take(1);
		if (lengthTypeID.equals("0")) {
			long length = binaryToDecimal(bits.take(15));
			long end = bits.pos() + length;
			while (bits.pos() < end) {
				results.add(decode(bits));
			}
		} else {
			long numSubpackets = binaryToDecimal(bits.take(11));
			for (int i = 0; i < numSubpackets; i++) {
				results.add(decode(bits));
			}
		}

		return doMath(packetType, results);
	}

	public static void main(String[] args) {
		// decode(convertToBinary("D2FE28"));
		// decode(convertToBinary("38006F45291200"));
		// decode(convertToBinary("EE00D40C823060"));
		// decode(convertToBinary("8A004A801A8002F478"));
		// decode(convertToBinary("620080001611562C8802118E34"));
		// decode(convertToBinary("C0015000016115A2E0802F182340"));

		// decode(convertToBinary("C200B40A82")); // sum [1, 2]
		// decode(convertToBinary("04005AC33890")); // product [6, 9]
		// decode(convertToBinary("880086C3E88112")); // minimum [7, 8, 9]
		// decode(convertToBinary("CE00C43D881120")); // maximum [7, 8, 9]
		// decode(convertToBinary("D8005AC2A8F0")); // less than [5, 15] -> 1
		// decode(convertToBinary("F600BC2D8F")); // greater than [5, 15] -> 0
		// decode(convertToBinary("9C005AC2F8F0")); // equal [5, 15] -> 0
		// decode(convertToBinary("9C0141080250320F1802104A08")); // 1 + 3 = 2 * 2 -> 1

		Taker bits = new Taker(convertToBinary("005532447836402684AC7AB3801A800021F0961146B1007A1147C89440294D005C12D2A7BC992D3F4E50C72CDF29EECFD0ACD5CC016962099194002CE31C5D3005F401296CAF4B656A46B2DE5588015C913D8653A3A001B9C3C93D7AC672F4FF78C136532E6E0007FCDFA975A3004B002E69EC4FD2D32CDF3FFDDAF01C91FCA7B41700263818025A00B48DEF3DFB89D26C3281A200F4C5AF57582527BC1890042DE00B4B324DBA4FAFCE473EF7CC0802B59DA28580212B3BD99A78C8004EC300761DC128EE40086C4F8E50F0C01882D0FE29900A01C01C2C96F38FCBB3E18C96F38FCBB3E1BCC57E2AA0154EDEC45096712A64A2520C6401A9E80213D98562653D98562612A06C0143CB03C529B5D9FD87CBA64F88CA439EC5BB299718023800D3CE7A935F9EA884F5EFAE9E10079125AF39E80212330F93EC7DAD7A9D5C4002A24A806A0062019B6600730173640575A0147C60070011FCA005000F7080385800CBEE006800A30C023520077A401840004BAC00D7A001FB31AAD10CC016923DA00686769E019DA780D0022394854167C2A56FB75200D33801F696D5B922F98B68B64E02460054CAE900949401BB80021D0562344E00042A16C6B8253000600B78020200E44386B068401E8391661C4E14B804D3B6B27CFE98E73BCF55B65762C402768803F09620419100661EC2A8CE0008741A83917CC024970D9E718DD341640259D80200008444D8F713C401D88310E2EC9F20F3330E059009118019A8803F12A0FC6E1006E3744183D27312200D4AC01693F5A131C93F5A131C970D6008867379CD3221289B13D402492EE377917CACEDB3695AD61C939C7C10082597E3740E857396499EA31980293F4FD206B40123CEE27CFB64D5E57B9ACC7F993D9495444001C998E66B50896B0B90050D34DF3295289128E73070E00A4E7A389224323005E801049351952694C000"));
		System.out.println("FINAL OUTPUT: " + decode(bits));
		System.out.println("VERSION SUM: " + bits.version());
	}
}
