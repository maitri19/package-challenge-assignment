package com.mobiquityinc.dto;

/**
 * The Class ItemDto.
 */
public class ItemDto {

	/** The id. */
	private int id;

	/** The weight. */
	private double weight;

	/** The cost. */
	private int cost;

	/**
	 * Instantiates a new item dto.
	 *
	 * @param weight the weight
	 * @param cost   the cost
	 */
	public ItemDto(double weight, int cost) {
		super();
		this.weight = weight;
		this.cost = cost;
	}

	/**
	 * Instantiates a new item dto.
	 *
	 * @param id     the id
	 * @param weight the weight
	 * @param cost   the cost
	 */
	public ItemDto(int id, double weight, int cost) {
		super();
		this.id = id;
		this.weight = weight;
		this.cost = cost;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public double getWeight() {
		return Math.round(weight * 100.0) / 100.0;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * Gets the ratio.
	 *
	 * @return the ratio
	 */
	public double getRatio() {
		return this.cost / this.weight;
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
		result = prime * result + cost;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ItemDto itemDto = (ItemDto) o;
		return Double.compare(itemDto.weight, weight) == 0 && cost == itemDto.cost;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ItemDto [id=" + id + ", weight=" + weight + ", cost=" + cost + "]";
	}

}
