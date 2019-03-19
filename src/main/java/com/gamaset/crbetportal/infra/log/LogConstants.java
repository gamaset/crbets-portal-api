package com.gamaset.crbetportal.infra.log;

/**
 * 
 * @since 1.0.0
 * @author Christopher Rozario (ˇ෴ˇ) CREATE, TEST, COMPILE AND RUN.
 * @date 2018-jan-08
 */
public class LogConstants {

	/**
	 * Error messages
	 */
	
	public static final String CANNOT_BE_UPDATED_ADDRESS = "Cannot be updated address";
	public static final String CANNOT_BE_CREATED_ADDRESS = "Cannot be created address";
	public static final String ERROR_ON_GET_CUSTOMER_BY_ID = "Error on GET Customer with ID";
	public static final String ERROR_GET_CUSTOMER_WITH_FILTER_PARAMS = "Error on GET Customer with filter params";
	public static final String ERROR_GET_ACCOUNT_BY_CUSTOMER = "Error on GET account by customerId";
	public static final String ERROR_ON_CREATED_CUSTOMER = "Customer cannot be created with name and taxId";
	public static final String ERROR_CUSTOMER_DETAIL_CANNOT_BE_UPDATED = "Customer Detail cannot be updated with customerId";
	public static final String CUSTOMER_CANNOT_BE_UPDATED = "Customer cannot be updated with name and taxId";
	public static final String CARD_GENERATION_ERROR = "Card Generation error with accountId";
	public static final String GET_ACCOUNT_ERROR = "Get Account error with accountId";
	public static final String GET_BUSINESS_ORIGIN_ERROR = "Get Business Origin error";
	public static final String LIST_COMPANIES_ERROR = "Getting list companies error";
	public static final String ERROR_ON_CREATED_COMPANY = "Company cannot be created";
	public static final String ERROR_ON_DO_FINANCIAL_ADJUST = "Error on do financial adjust";
	public static final String ERROR_ON_LIST_FINANCIAL_ADJUST = "Error on GET financial adjust";
	public static final String ERROR_GET_TRANSACTIONS_ACCOUNT = "Error on Get transactions account";
	public static final String ERROR_GET_PRODUCTS = "Error on Get products";

	/**
	 * Default Messages
	 */
	public static final String GETTING_LIST_OF_COMPANIES = "Getting list of companies";
	public static final String GETTING_LIST_OF_CUSTOMERS = "Getting list of customers";
	public static final String CUSTOMER_NOT_FOUND = "Customer not found with ID";
	public static final String UPDATING_CUSTOMER_ADDRESS = "Updating Customer Address";
	public static final String CREATING_CUSTOMER_ADDRESS = "Creating Customer Address";
	public static final String GETTING_LIST_OF_PHONE_TYPE = "Getting list of phone types";
	public static final String GETTING_LIST_OF_OCCUPATIONS = "Getting list of occupations";
	public static final String GETTING_LIST_OF_ADDRESS_TYPE = "Getting list of address types";
	public static final String GETTING_LIST_OF_CAREERS = "Getting list of careers";
	public static final String GETTING_CUSTOMER = "Getting customer";
	public static final String GETTING_ZIPCODE_DATA = "Getting Zipcode data";
	public static final String GETTING_LIST_OF_MARITAL_STATUS = "Getting list of marital status";
	public static final String GETTING_LIST_OF_NATIONALITIES = "Getting list of nationalities";
	public static final String GETTING_LIST_OF_BUSINESS_ORIGIN = "Getting list of business origin";
	public static final String GETTING_LIST_OF_FINANCIAL_ADJUSTS = "Getting list of financial adjusts";
	public static final String DOING_FINANCIAL_ADJUSTS = "Doing a financial adjusts";
	public static final String GETTING_BUSINESS_ORIGIN = "Getting business origin";
	public static final String GETTING_ADDRESS = "Getting address";
	public static final String GETTING_CUSTOMER_ACCOUNTS = "Getting customer accounts";
	public static final String GETTING_FINANCIAL_ADJUSTS = "Getting financial adjusts";
	public static final String GENERATE_CARD_BY_ACCOUNT = "Generating Card for account";
	public static final String CREATING_COMPANY = "Creating Company";
	public static final String GET_ACCOUNT_BY_ID = "Get account by ID";
	public static final String GETTING_TRANSACTIONS_ACCOUNT = "Getting transactions account";
	public static final String GETTING_LIST_OF_PRODUCTS = "Getting list of products";
	
	/**
	 * CACHE
	 */
	public static final String REDIS_DOWN = "REDIS DOWN";
	public static final String REDIS_UP = "REDIS UP";
	public static final String CACHE_SERIALIZATION = "Cache Serialization";

	/**
	 * Class
	 */
	public static final String CLASS_NAME = "className";
	public static final String CLASS_METHOD = "classMethod";

	/**
	 *
	 */
	public class ACCESS {
		public static final String HTTP_METHOD = "method";
		public static final String HTTP_STATUS = "httpStatus";

		private ACCESS() {
		}
	}

	/**
	 *
	 */
	public class PARAMETERS {

		public static final String PAGE = "page";
		public static final String SIZE = "size";
		public static final String NAME = "name";
		public static final String TAX_ID = "taxId";
		public static final String CUSTOMER_ID = "customerId";
		public static final String BUSINESS_ORIGIN_ID = "businessOriginId";
		public static final String ACCOUNT_ID = "accountId";
		public static final String ADJUST_TYPE = "adjustType";
		public static final String ADJUST_DATE = "adjustDate";
		public static final String ADJUST_VALUE = "adjustValue";
		public static final String PROCESSED = "processed";

		private PARAMETERS() {
		}
	}

	/**
	 *
	 */
	public class FIELDS {

		public static final String FIELD_BUSINESS_ORIGIN_ID = "businessOriginId";
		public static final String FIELD_CUSTOMER_ID = "customerId";
		public static final String FIELD_ACCOUNT_ID = "accountId";
		public static final String FIELD_PROCESSED = "processed";

		private FIELDS() {
		}
	}

	/**
	 *
	 */
	public class ACTION {

		private ACTION() {
		}
	}
}
