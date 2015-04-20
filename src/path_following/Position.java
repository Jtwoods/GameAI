package path_following;

/**
 * Position tracks a position in the x and z plane
 * @author James Woods
 *
 */
public class Position {
	
	/**
	 * x is the x position of the Position.
	 */
	public int x;
	
	/**
	 * z is the z position of the Position.
	 */
	public int z;
	
	/**
	 * Position constructs a position with the desired 
	 * location.
	 * @param x the x position.
	 * @param z the z position.
	 */
	public Position(int z , int x) {
		this.z = z;
		this.x = x;
	}

	/**
	 * gets the x position.
	 * @return the x position.
	 */
	public int getX() {
		return x;
	}

	/**
	 * sets the x position.
	 * @param x the new x position.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * gets the z position.
	 * @return the z position.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * sets the z position.
	 * @param z the new z position.
	 */
	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	
	
}
