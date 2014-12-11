package car.simple.service.database;

/**
 * Created by r.savuschuk on 11/11/2014.
 */
public class DbError
 {
	public enum ErrorCode {
		/**
		 * Wrong input param value
		 */
		WRONG_DATA,
		/**
		 * Data can't be saved into database
		 */
		CANT_SAVE_DATA,
		/**
		 * Data can't be removed from database
		 */
		CANT_REMOVE_DATA,
		/**
		 * Data can't be updated in database
		 */
		CANT_UPDATE_DATA,
		/**
		 * Data can't be obtained from database
		 */
		CANT_OBTAIN_DATA,
		/**
		 *
		 */
		FULL_LIBRARY,
		/**

		 */
		FULL_PLAYLIST
	}

	private final ErrorCode code;
	private String description;

	public DbError(ErrorCode code) {
		this.code = code;
	}

	public  DbError( ErrorCode code, String description) {
		this.code = code;
		this.description = description;
	}

	public  ErrorCode getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Error{" + "code=" + code + ", description='" + description + '\'' + '}';
	}
}
