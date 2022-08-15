
# leaveApplication
Internship project


ADMIN:
	
[POST] api/auth/login :: "userName":"admin,"password":"admin123"

[POST] api/createLeaveType :: "name":"sick leave" || "name":"casual leave"

[POST] api/create-yearly-leave-balance :: "year" :"2022", "maxDay":20, "leaveType":{"id":1,"name":"sick leave"}

[POST] api/auth/registerUser :: "userName":"user", "password":"user123", "email":"user@gmail.com","manager":managerID  
      (( it will also create leave a balance sheet for the user upon registering the user.)) 
      ((One issue is, first the admin has to create LEAVETYPE and YEARLY LEAVE BALANCE))

[GET] api/auth/logout





USER:

[POST] api/auth/login :: {"userName":"username","password":"userpassword"}

[POST] api/userLeave (create leave application) :: "fromDate":"2022-02-10","toDate":"2022-02-11","remark":"need vacation", "leaveType":{"id":1/2}

[GET] api/getAllLeavesByStatus/{status} -- status:PENDING, APPROVED, REJECTED.

[GET] api/getAllLeavesByType/{leaveType} -- leaveType:sick leave, casual leave.

[GET] api/getLeaveBalanceofaUser/ 

[PUT] api/auth/change-password :: requestparam{"oldPassword"},{"newPassword"}

[GET] api/auth/logout





MANAGER:

[GET] api/getAllLeavesByStatus/{status} -- status:PENDING, APPROVED, REJECTED.

[GET] api/getAllLeavesByType/{leaveType} -- leaveType:sick leave, casual leave.

[PUT] api/approveOrDenyLeave/{id} -- id is leaveapplications id;

[GET] api/getLeaveBalances

[PUT] api/auth/change-password :: requestparam{"oldPassword"},{"newPassword"}

[GET] api/auth/logout




		
