package com.mobiquityinc.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PackageDto.
 */
public class PackageDto {

	/** The item list. */
	private List<ItemDto> itemList;

	/** The max weight. */
	private int maxWeight;

	/**
	 * Instantiates a new package dto.
	 *
	 * @param maxWeight the max weight
	 */
	public PackageDto(int maxWeight) {
		this.maxWeight = maxWeight;
		this.itemList = new ArrayList<>();
	}

	/**
	 * Instantiates a new package dto.
	 *
	 * @param itemList  the item list
	 * @param maxWeight the max weight
	 */
	public PackageDto(List<ItemDto> itemList, int maxWeight) {
		super();
		this.itemList = itemList;
		this.maxWeight = maxWeight;
	}

	/**
	 * Gets the item list.
	 *
	 * @return the item list
	 */
	public List<ItemDto> getItemList() {
		return itemList;
	}

	/**
	 * Sets the item list.
	 *
	 * @param itemList the new item list
	 */
	public void setItemList(List<ItemDto> itemList) {
		this.itemList = itemList;
	}

	/**
	 * Gets the max weight.
	 *
	 * @return the max weight
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

	/**
	 * Sets the max weight.
	 *
	 * @param maxWeight the new max weight
	 */
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemList == null) ? 0 : itemList.hashCode());
		result = prime * result + maxWeight;
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		PackageDto other = (PackageDto) obj;
		if (itemList == null) {
			if (other.itemList != null)
				return false;
		} else if (!itemList.equals(other.itemList))
			return false;

		return Math.round(maxWeight * 100.0) / 100.0 == Math.round(other.maxWeight * 100.0) / 100.0;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "PackageDto [itemList=" + itemList + ", maxWeight=" + maxWeight + "]";
	}

	/**
	 * Exists.
	 *
	 * @param itemDto the item dto
	 * @return true, if successful
	 */
	public boolean exists(ItemDto itemDto) {

		boolean exist = false;
		int lastIndex = this.getItemList().size() - 1;
		for (int j = lastIndex; j >= 0; j--) {
			ItemDto currItem = this.getItemList().get(j);
			if (currItem.equals(itemDto)) {
				exist = true;
				break;
			}

		}
		return exist;
	}

}
