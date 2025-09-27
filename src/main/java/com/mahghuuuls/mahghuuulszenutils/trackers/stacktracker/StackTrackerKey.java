package com.mahghuuuls.mahghuuulszenutils.trackers.stacktracker;

import java.util.Objects;

class StackTrackerKey {
	private final String ownerId;
	private final String stackName;

	StackTrackerKey(String ownerId, String stackName) {
		this.ownerId = ownerId;
		this.stackName = stackName;
	}

	@Override
	public boolean equals(Object object) {

		if (this == object) {
			return true;
		}

		if (!(object instanceof StackTrackerKey)) {
			return false;
		}

		StackTrackerKey that = (StackTrackerKey) object;
		return Objects.equals(ownerId, that.ownerId) && Objects.equals(stackName, that.stackName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ownerId, stackName);
	}
}
