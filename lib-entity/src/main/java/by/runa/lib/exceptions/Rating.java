package by.runa.lib.exceptions;

import lombok.Getter;

@Getter

public enum Rating {
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

	@Getter
	private final int value;

	Rating(int value) {
		this.value = value;
	}

	public static Rating findRatingFromInt(int num) {
		for (Rating b : Rating.values()) {
			if (b.value == num) {
				return b;
			}
		}
		return null;
	}
}
