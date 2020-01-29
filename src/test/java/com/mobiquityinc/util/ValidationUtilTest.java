package com.mobiquityinc.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mobiquityinc.dto.ItemDto;
import com.mobiquityinc.dto.PackageDto;
import com.mobiquityinc.exception.APIException;

public class ValidationUtilTest {

	static File invalidSizePackage;

	@Test
	public void testValidateItemListSize() throws Exception {
		invalidSizePackage = new File(getClass().getClassLoader().getResource("invalid_size_package.txt").getFile());
		String line = "";
		try (BufferedReader fileContent = new BufferedReader(
				new FileReader(URLDecoder.decode(invalidSizePackage.getAbsolutePath(), "UTF-8")))) {
			while ((line = fileContent.readLine()) != null) {

				// Parsing line
				String[] packag = line.split(" : ");

				List<String> items = Arrays.asList(packag[1].split(" "));
				ValidationUtil.validateItemListSize(items);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidateItemsWeights() throws Exception {
		String errorMessage = "Max item weight possible is";
		PackageDto packageDto = new PackageDto(89);
		packageDto.getItemList().add(new ItemDto(1, 4.98, 2));
		packageDto.getItemList().add(new ItemDto(2, 112.78, 30));
		packageDto.getItemList().add(new ItemDto(2, 12.78, 30));

		APIException exception = assertThrows(APIException.class,
				() -> ValidationUtil.validateItemsWeights(packageDto));

		assertTrue(exception.getMessage().contains(errorMessage));
	}

	@Test
	public void testValidateItemsCosts() throws Exception {
		String errorMessage = "Max item cost possible is";
		PackageDto packageDto = new PackageDto(89);
		packageDto.getItemList().add(new ItemDto(1, 4.98, 2));
		packageDto.getItemList().add(new ItemDto(2, 112.78, 130));
		APIException exception = assertThrows(APIException.class, () -> ValidationUtil.validateItemsCosts(packageDto));
		assertTrue(exception.getMessage().contains(errorMessage));
	}

}
