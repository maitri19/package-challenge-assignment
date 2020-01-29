package com.mobiquityinc.packer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.mobiquityinc.common.CommonConstants;
import com.mobiquityinc.dto.ItemDto;
import com.mobiquityinc.dto.PackageDto;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.finder.PackageChallenge;
import com.mobiquityinc.finder.service.PackageChallengeService;
import com.mobiquityinc.util.ValidationUtil;

/**
 * The Class Packer.
 */
public class Packer {

	/**
	 * Restrict instantiates a new packer.
	 */
	private Packer() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * This is entry point method for the API
	 *
	 * @param filePath the arguments
	 * @throws APIException the API exception
	 */
	public static String pack(String filePath) throws APIException {

		String line = "";
		StringBuilder result = new StringBuilder();
		PackageChallengeService service = new PackageChallenge();

		if (filePath == null || filePath.isEmpty()) {
			throw new APIException("Invalid parameter: File path required");
		}
		File file = new File(filePath);
		try (BufferedReader fileContent = new BufferedReader(new FileReader(file))) {
			while ((line = fileContent.readLine()) != null) {

				// Split each line
				String[] currentPackage = line.split(" : ");
				int allowedWeight = Integer.parseInt(currentPackage[0]);
				PackageDto packageDto = new PackageDto(allowedWeight);
				// split item list in the package
				List<String> items = Arrays.asList(currentPackage[1].split(" "));
				if (!items.isEmpty()) {
					ValidationUtil.validateItemListSize(items);
					packageDto = prepareItemlist(packageDto, items, line);
					ValidationUtil.validateItemsCosts(packageDto);
					ValidationUtil.validateItemsWeights(packageDto);

					result.append(service.findItemsInPackage(packageDto) + "\n");
				}

			}

		} catch (IOException e) {
			throw new APIException("Invalid parameter: " + e.getMessage());
		} catch (NumberFormatException n) {
			throw new APIException(String.format("Invalid input parameter for line %s ", line));
		}
		return result.toString().trim();
	}

	/**
	 * Prepare itemlist with cost, weight and id for the current package
	 *
	 * @param packageDto the package dto
	 * @param items      the items
	 * @return the package dto
	 * @throws APIException
	 */
	private static PackageDto prepareItemlist(PackageDto packageDto, List<String> items, String line)
			throws APIException {
		for (String item : items) {

			String[] itemString = (item.substring(1, item.length() - 1)).split(",");
			int id = Integer.parseInt(itemString[0]);
			double weight = Double.parseDouble(itemString[1]);
			if (!itemString[2].startsWith(CommonConstants.EURO_CURRENCY_SYMBOL)) {
				throw new APIException(String.format("Invalid input parameter, missing € symbol %s", line));
			}
			int cost = Integer.parseInt(itemString[2].substring(1, itemString[2].length()));
			// higher than allowed weight items are not included in the itemlist
			if (weight <= packageDto.getMaxWeight()) {
				ItemDto itemDto = new ItemDto(id, weight, cost);
				packageDto.getItemList().add(itemDto);
			}

		}
		return packageDto;
	}
}
