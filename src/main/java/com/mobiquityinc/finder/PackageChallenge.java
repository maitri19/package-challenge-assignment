package com.mobiquityinc.finder;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.mobiquityinc.dto.ItemDto;
import com.mobiquityinc.dto.PackageDto;
import com.mobiquityinc.finder.service.PackageChallengeService;


/**
 * The Class PackageChallenge.
 */
public class PackageChallenge implements PackageChallengeService{

	/**
	 * Find the items for package with highest cost and lowest weight.
	 *
	 * @param packageDto    the package dto
	 * @return the string
	 */
	public String findItemsInPackage(PackageDto packageDto) {
		packageDto.getItemList().add(new ItemDto(0, 0, 0));
		// sort the itemslist by ratio, which is cost/weight, then reverse order to keep the optimal item on top
		packageDto.getItemList().sort(Comparator.comparing(ItemDto::getRatio).reversed());
		List<PackageDto> processingList = new ArrayList<>();
		PackageDto procDto = new PackageDto(packageDto.getMaxWeight());
		procDto.getItemList().add(packageDto.getItemList().get(0));
		processingList.add(procDto);
		List<PackageDto> combinationList = createItemListWithAllCombinations(packageDto, processingList);
		List<ItemDto> resultList = searchBestItemsFromCombinationList(packageDto, combinationList);
		String result = resultList.stream().map(ItemDto::getId).map(String::valueOf).collect(Collectors.joining(", "));
		result = result.isEmpty() ? "-" : result;
		return result;
	}

	/**
	 * Creates the item list with all combinations.
	 *
	 * @param packageDto     the package dto
	 * @param processingList the processing list
	 * @return the list
	 */
	private static List<PackageDto> createItemListWithAllCombinations(PackageDto packageDto,
			List<PackageDto> processingList) {

		for (int i = 1; i < packageDto.getItemList().size(); i++) {
			PackageDto currentDto = processingList.get(i - 1);
			ItemDto itemOfMainPackage = packageDto.getItemList().get(i);
			// to create extended list add current item cost and weight with previous list items 
			// costs and weights then check weight again not to be greater than package allowed maximum weight
			List<ItemDto> extendedList = currentDto.getItemList().stream()
					.map(t -> addCostAndWeight(t, itemOfMainPackage))
					.filter(t -> checkItemWeight(t, packageDto.getMaxWeight()))
					.collect(Collectors.toCollection(ArrayList::new));
			// combine 2 lists & remove duplicates
			Set<ItemDto> set = new LinkedHashSet<>(currentDto.getItemList());
			set.addAll(extendedList);
			List<ItemDto> combinedList = new ArrayList<>(set);
			List<ItemDto> copyOfCombinedList = new ArrayList<>(set);
			
			for (ItemDto itemDto : copyOfCombinedList) {
			    // remove the item from combinations if another item is better than that
				combinedList.removeIf(t -> t.getWeight() > itemDto.getWeight() && t.getCost() <= itemDto.getCost());
				combinedList.removeIf(t -> t.getWeight() == itemDto.getWeight() && t.getCost() < itemDto.getCost());
			}

			processingList.add(new PackageDto(combinedList, currentDto.getMaxWeight()));

		}
		return processingList;
	}

	/**
	 * search the best items from CombinationList which results in maximized package
	 * cost.
	 *
	 * @param packageDto     the package dto
	 * @param processingList list of Items sets resulted from createItemListWithAllCombinations
	 *                       method.
	 * @return Selected optimal items can be chosen to maximise the cost.
	 */
	private static List<ItemDto> searchBestItemsFromCombinationList(PackageDto packageDto,
			List<PackageDto> processingList) {
	    // Start from last itemlist
		int lastItemListIndex = processingList.size() - 1;
		// pick up the item with maximum cost
		Optional<ItemDto> lastItemOptional = processingList.get(lastItemListIndex).getItemList().stream()
				.max(Comparator.comparingInt(ItemDto::getCost));

		ItemDto lastItem = lastItemOptional.isPresent() ? lastItemOptional.get() : new ItemDto(0, 0);
		List<ItemDto> resultList = new ArrayList<>();

		int currentMaxCost = lastItem.getCost();
		double currentMaxWeight = Math.round(lastItem.getWeight() * 100.0) / 100.0;
		ItemDto prevItemDto = lastItem;
		// if item is found in current list then check previous item list 
		for (int i = lastItemListIndex - 1; i >= 0; i--) {
			int prevItemListIndex = i + 1;
			PackageDto currList = processingList.get(i);
			boolean exist = currList.exists(prevItemDto);
			// Pair (weight and cost) not found in previous list; index of that itemlist will give item from package, which is included in result
			if (!exist) {
				resultList.add(packageDto.getItemList().get(prevItemListIndex));
				currentMaxCost -= packageDto.getItemList().get(prevItemListIndex).getCost();

				currentMaxWeight = currentMaxWeight - packageDto.getItemList().get(prevItemListIndex).getWeight();
				currentMaxWeight = Math.round(currentMaxWeight * 100.0) / 100.0;
				prevItemDto = new ItemDto(currentMaxWeight, currentMaxCost);
			} 
		}
		return resultList;
	}

	/**
	 * Check items weights to be under maximum allowed defined in a package.
	 *
	 * @param item      represents an item in a package.
	 * @param maxWeight maximum allowed weight a package can take,defined in a
	 *                  package.
	 * @return true if item weight is less than or equal maximum weight,otherwise
	 *         false.
	 */
	private static boolean checkItemWeight(ItemDto item, int maxWeight) {
		return (item.getWeight() <= maxWeight);
	}

	/**
	 * add two items costs and weights and return new item.
	 *
	 * @param item1 represents an item in a package.
	 * @param item2 represents an item in a package.
	 * @return new item result from adding given two items.
	 */
	private static ItemDto addCostAndWeight(ItemDto item1, ItemDto item2) {
		int processingCost = item1.getCost() + item2.getCost();
		double processingWeight = item1.getWeight() + item2.getWeight();

		return new ItemDto(item2.getId(), Math.round(processingWeight * 100.0) / 100.0, processingCost);
	}
}
