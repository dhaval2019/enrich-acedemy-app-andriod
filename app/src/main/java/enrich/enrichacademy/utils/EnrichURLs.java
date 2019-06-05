package enrich.enrichacademy.utils;

/**
 * Created by Admin on 02-Mar-17.
 */

public class EnrichURLs {

    public static NetworkConnector retrofitNetworkHandler;
    public static NetworkConnector retrofitNetworkHandlerForEnrich;

    //    public static final String HOST = "http://api.zivadiva.com/v2/"; //STAGING
//
//    public static final String ENRICH_HOST = "http://zivadiva.com:9999/EnrichAcademyMaven/"; //STAGING

    //    public static final String ENRICH_HOST = "http://192.168.2.223:8080/EnrichAcademyMaven/"; //LOCALHOST
    public static final String ENRICH_HOST = "http://enrichsalon.com:8080/EnrichAcademyMaven/"; //PROD

//    public static final String COURSES = "api/Course";
//    public static final String CATEGORIES = "api/Category";
//    public static final String SERVICES = "api/Service";
//    public static final String TOPOLOGY = "api/Topology";
//    public static final String TIMINGS = "api/Timing";
//    public static final String ORDER = "api/Order";
//    public static final String ADD_CUSTOMER = "api/Customer";


    public static final String HOST = "http://54.179.143.59:3000/api/"; //STAGING

    public static final String COURSES = "Courses";
    public static final String CATEGORIES = "api/Category";
    public static final String SERVICES = "Services";
    public static final String TOPOLOGY = "Topologies";
    public static final String TIMINGS = "api/Timing";
    public static final String ORDER = "api/Order";
    public static final String ADD_CUSTOMER = "api/Customer";

    public static final String GET_ALL_TOPOLOGIES = "Topology/GetAllActiveTopologies";
    public static final String GET_ALL_SERVICES = "Service/GetAllActiveServices";
    public static final String GET_ALL_SERVICES_BY_STORE_ID = "Service/GetAllActiveServicesByStoreId/";
    public static final String GET_ALL_ACTIVE_SERVICES_BY_STORE_ID = "Service/GetAllActiveServicesByStoreId/";
    public static final String GET_DATE_TIME_SLOTS = "Service/GetDateTimeSlotsForAService";
    public static final String GET_ALL_COURSE_CATEGORY = "CourseCategory/GetAllCourseCategories";
    public static final String GET_ALL_COURSES = "Course/GetAllActiveCourses";
    public static final String GET_ALL_COURSES_BY_ID = "Course/GetAllActiveCoursesByUserid";
    public static final String VERIFY_NUMBER = "User/VerifyOTP";
    public static final String LOGIN = "User/SendOTPForLogin";
    public static final String VERIFY_OTP_LOGIN = "User/VerifyOTPForLogin";
    public static final String GET_ORDER_BY_ID = "Order/GetOrderById";
    public static final String GET_CURRENT_APPOINTMENTS = "Order/GetCurrentAppointments";
    public static final String GET_PAST_APPOINTMENTS = "Order/GetPastAppointments";
    public static final String GET_USER_BY_ID = "User/GetUserById";
    public static final String SEARCH_BY_KEYWORD = "Search/SearchByKeyword/";
    public static final String GET_APPLIED_COURSES = "CourseApplication/GetUserAppliedCourses/";
    public static final String GET_CITIES = "City/GetAllActiveCities";
    public static final String GET_STORES = "Store/GetAllActiveStores";
    public static final String GET_COURSE_CATEGORY = "CourseCategory/GetAllCourseCategories";

}
