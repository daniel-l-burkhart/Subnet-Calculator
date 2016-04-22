package edu.westga.subnetCalculator.model;

import java.util.StringTokenizer;

/**
 * A class to find the subnet based on an IP Address and the corresponding
 * subnet mask.
 * 
 * @author Daniel Burkhart
 * @version n/a
 */
public class SubnetCalc {

	/**
	 * Method to find the subnet by comparing the address and the mask
	 * 
	 * @param ipAddress
	 *            the ipAddress to be compared with the subnet mask
	 * @param numMaskBits
	 *            the number of bits of the subnet mask
	 * @return returns the subnet after comparing the ip address and the mask
	 */
	public static String findSubnet(String ipAddress, int numMaskBits) {

		String stringMask = "";

		if (ipAddress == null) {
			throw new IllegalArgumentException("Ip Address is null");
		}

		if (numMaskBits < 0 || numMaskBits > 32) {
			throw new IllegalArgumentException("Number of bits must be between 0 and 32");
		}

		if (numMaskBits == 0) {
			stringMask = "0.0.0.0";
		}

		else {
			stringMask = shiftNumMaskBits(numMaskBits, stringMask);
		}

		return findSubnet(ipAddress, stringMask);
	}

	private static String shiftNumMaskBits(int numMaskBits, String stringMask) {
		try {
			int shift = (1 << 31);
			for (int count = numMaskBits - 1; count > 0; count--) {
				shift = (shift >> 1);
			}
			stringMask = Integer.toString((shift >> 24) & 255) + "." + Integer.toString((shift >> 16) & 255) + "."
					+ Integer.toString((shift >> 8) & 255) + "." + Integer.toString(shift & 255);

		} catch (Exception exception) {
			exception.getMessage();
		}
		return stringMask;
	}

	/**
	 * finds the subnet after comparing the ip address and the subnet mask
	 * 
	 * @param ipAddress
	 *            the ip address of the user
	 * @param subnetMask
	 *            the mask to be applied to the address to see if the packet
	 *            will be accepted or rejected
	 * @return the subnet
	 */
	public static String findSubnet(String ipAddress, String subnetMask) {

		String stringSubnet = new String();
		StringTokenizer ipAddressTokens = new StringTokenizer(ipAddress, ".");
		StringTokenizer subnetMaskTokens = new StringTokenizer(subnetMask, ".");

		if (ipAddressTokens.countTokens() != 4 || subnetMaskTokens.countTokens() != 4) {
			throw new IllegalArgumentException("The IP Address and the Subnet Mask must have 4 octects");
		}

		for (int count = 0; count < 4; count++) {

			int ipAddressOctet = Integer.parseInt(ipAddressTokens.nextToken());
			int subnetMaskOctet = Integer.parseInt(subnetMaskTokens.nextToken());

			if (ipAddressOctet > 255 || subnetMaskOctet > 255 || subnetMaskOctet < 0 || ipAddressOctet < 0) {
				throw new IllegalArgumentException(
						"Each octet in the IP Address and Subnet Mask can only be between 0 and 255.");
			}

			Integer subnet = ipAddressOctet & subnetMaskOctet;
			String subnetOctet = subnet.toString();

			if (count == 3) {
				stringSubnet += (subnetOctet);
			} else {
				stringSubnet += (subnetOctet + ".");
			}

		}

		System.out.println("The IP Address entered is: \n" + ipAddress + "\n");
		System.out.println("The Subnet Mask entered is: \n" + subnetMask + "\n");
		System.out.println("The resulting subnet is: \n" + stringSubnet);
		return stringSubnet;
	}

	/**
	 * main method for class.
	 * 
	 * @param args
	 *            the arguments of the main method.
	 */
	public static void main(String[] args) {
		findSubnet("192.168.120.100", 5);
	}
}
