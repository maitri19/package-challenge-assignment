package com.mobiquityinc.finder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mobiquityinc.dto.ItemDto;
import com.mobiquityinc.dto.PackageDto;

public class PackageChallengeTest {

    @Test
    public void testFindPackageWithOutput() throws Exception {
        PackageDto packageDto = new PackageDto(81);
        packageDto.getItemList().add(new ItemDto(1, 53.87, 32));
        packageDto.getItemList().add(new ItemDto(2, 88.62, 98));
        packageDto.getItemList().add(new ItemDto(3, 78.48, 3));
        packageDto.getItemList().add(new ItemDto(4, 72.30, 76));
        packageDto.getItemList().add(new ItemDto(5, 30.18, 9));
        packageDto.getItemList().add(new ItemDto(6, 46.34, 48));
        PackageChallenge finder = new PackageChallenge();
        String result = finder.findItemsInPackage(packageDto);
        
        assertEquals(result, "4");

    }

    @Test
    public void testFindPackageWithNoOutput() throws Exception {
        PackageDto packageDto = new PackageDto(9);
        packageDto.getItemList().add(new ItemDto(1, 13.87, 32));
        packageDto.getItemList().add(new ItemDto(2, 12.78, 30));
        PackageChallenge finder = new PackageChallenge();
        String result = finder.findItemsInPackage(packageDto);

        assertEquals(result, "-");

    }

}
