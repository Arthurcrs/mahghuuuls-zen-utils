package com.mahghuuuls.mahghuuulszenutils.core.stacktracker;

import java.util.Locale;

public enum RefreshRule {
	PRESERVE, RESET, ADD;

	public static RefreshRule fromString(String refreshRule) {

		if (refreshRule == null) {
			throw new IllegalArgumentException("Refresh rule cannot be null");
		}

		String normalizedValue = refreshRule.trim().toUpperCase(Locale.ROOT);

		try {
			return RefreshRule.valueOf(normalizedValue);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException(
					"Invalid refresh rule '" + refreshRule + "'. Valid options are: preserve, reset, add.");
		}
	}
}
