namespace cpp dispatch.server.thrift.backend
namespace java dispatch.server.thrift.backend
namespace py dispatch.server.thrift.backend

/*---------------------------------------------------------------
  Errors
---------------------------------------------------------------*/

// incorrect request format/invalid input args, args validation error
exception BadRequest {
	1: string message, //error info for debug
}

// internal server error
exception InternalServerError {
	1: string message, //error info for debug
}

// overloaded server
exception Busy {
	1: string message, //error info for debug
}

// session is wrong or expired
exception Unauthorized {
	1: string message, //error info for debug
}

// user try to execute operation unacceptable for user's role
exception AccessDenied {
	1: string message, //error info for debug
}

// object does not found
exception ObjectNotFound {
	1: string message, //error info for debug
}

exception UserLicenseExpired {
	1: string message, //error info for debug
}

exception TrialIsNotActivated {
	1: string message, //error info for debug
}

exception LoginFailed {
	1: string message, //error info for debug
}

/*-----------------------------------------
  Data Structures
------------------------------------------*/
struct StoreFieldValue {
	1: string title,
	2: optional string value,
}

struct AdditionalFields {
	/** list with field values */
	1: list<StoreFieldValue> data,
}

struct Session {
	1: string id, //uuid
}

struct License {
	1: optional i64 expire, //unix time
	2: optional i32 monitoringObjectsLimit,
	3: optional i32 usersLimit,
	4: optional i32 smsLimit,
	5: optional bool enabled,
}

struct Group {
	1: string parentGroupId, //uuid
	2: string id, //uuid
	3: string title, //not empty
	4: optional License license,
	5: optional AdditionalFields additionalFields,
}

struct Tracker {
	1: string vendor, //not empty
	2: string model, //not empty
	3: list<string> identifier, //not empty, imei or id or imei/id
	4: optional string phoneNumber,
}

struct MonitoringObject {
	1: string parentGroupId, //uuid
	2: string id, //uuid
	3: string name, //not empty
	4: Tracker tracker,
	5: optional string displayColor,
	6: optional string displayIcon,
	7: optional AdditionalFields additionalFields,
}

service DispatchBackend {

	///////Login service////////
	/*
	 * returns session for some username
	 *
	 * @param userLoginName - user login name, not empty
	 * @param password - password, not empty
	 * @param longSession - type of session expiration time (true: 30d, false: 12h)
	 *
	 */
	Session login(
		1:string userLoginName,
		2:string password,
		3:bool longSession,
	) throws (
		1:BadRequest bre,
		2:Busy bse,
		3:InternalServerError ise,
		4:AccessDenied ade,
		5:UserLicenseExpired ule,
		6:TrialIsNotActivated tne,
		7:LoginFailed lfe,
	),

	/*
	 *
	 * @param session - user session
	 *
	 */
	void logout(
		1:Session session,
	) throws (
		1:BadRequest bre,
		2:Busy bse,
		3:InternalServerError ise,
	),
	///////END Login service////////

	///////Group service////////

	/*
	 * returns root group list for user
	 *
	 * @param session - user session
	 *
	 */
	list<Group> getRootGroups(
		1:Session session,
	) throws (
		1:BadRequest bre,
		2:Busy bse,
		3:InternalServerError ise,
		4:Unauthorized ue,
		5:AccessDenied ad,
	),

        /*
         * returns children groups list info by parent group id
         *
         * @param session - user session
         * @param parentGroupId - parent group id, not empty, uuid
         * @param recursive - return all subgroups
         *
         */
        list<Group> getChildrenGroups(
                1:Session session,
                2:string parentGroupId,
                3:bool recursive,
        ) throws (
                1:BadRequest bre,
                2:Busy bse,
                3:InternalServerError ise,
                4:Unauthorized ue,
                5:AccessDenied ad,
                6:ObjectNotFound one,
        ),

	/*
	 * returns new group info
	 *
	 * @param session - user session
	 * @param parentGroupId - parent group id, not empty, uuid
	 * @param title - new company title, not empty
	 * @param license - company license struct, not empty
	 * @param  additionalFields - additional fields
	 *
	 */
	Group createCompanyWithAdditionalFields(
		1:Session session,
		2:string parentGroupId,
		3:string title,
		4:License license,
		5:AdditionalFields additionalFields,
	) throws (
		1:BadRequest bre,
		2:Busy bse,
		3:InternalServerError ise,
		4:Unauthorized ue,
		5:AccessDenied ad,
		6:ObjectNotFound one,
	),
	/*
	 *
	 * @param session - user session
	 * @param data - new data for edited group
	 *
	 */
	void editGroup(
		1:Session session,
		2:Group data,
	) throws (
		1:BadRequest bre,
		2:Busy bse,
		3:InternalServerError ise,
		4:Unauthorized ue,
		5:AccessDenied ade,
		6:ObjectNotFound one,
	),
	///////END Group service////////

	///////Monitoring object service////////
	/*
         * returns objects list for parent group
         *
         * @param session - user session
         * @param parentGroupId - parent group id, not empty, uuid
         * @param recursive - return all objects from subgroups
         *
         */
        list<MonitoringObject> getChildrenMonitoringObjects(
                1:Session session,
                2:string parentGroupId,
                3:bool recursive,
        ) throws (
                1:BadRequest bre,
                2:Busy bse,
                3:InternalServerError ise,
                4:Unauthorized ue,
                5:AccessDenied ad,
                6:ObjectNotFound one,
        ),

	/*
	 * returns new monitoring object info
	 *
	 * @param session - user session
	 * @param parentGroupId - parent group id, not empty, uuid
	 * @param tracker - tracker device, not empty
	 * @param name - monitoring object name, not empty
	 * @param displayColor - display color
	 * @param displayIcon - display icon
	 * @param additionalFields - additional fields
	 *
	 */
	MonitoringObject createMonitoringObjectWithAdditionalFields(
		1:Session session,
		2:string parentGroupId,
		3:Tracker tracker,
		4:string name,
		5:string displayColor,
		6:string displayIcon,
		7:AdditionalFields additionalFields,
	) throws (
		1:BadRequest bre,
		2:Busy bse,
		3:InternalServerError ise,
		4:Unauthorized ue,
		5:AccessDenied ad,
		6:ObjectNotFound one,
	),

	/*
	 *
	 * @param session - user session
	 * @param data - new data for edited object
	 *
	 */
	void editMonitoringObject(
		1:Session session,
		2:MonitoringObject data,
	) throws (
		1:BadRequest bre,
		2:Busy bse,
		3:InternalServerError ise,
		4:Unauthorized ue,
		5:AccessDenied ade,
		6:ObjectNotFound one,
	)
}
