package com.sacco.system.modules.auth.model;

public enum Role {
    MEMBER,         // Standard access: View own savings/loans
    LOAN_OFFICER,   // Staff: Can approve/reject loan applications
    TREASURER,      // Board: Can view financial reports, approve disbursements
    SECRETARY,      // Board: Manages meeting minutes, member communication
    CHAIRPERSON,    // Board: Super admin access, strategic oversight
    ADMIN           // IT System Admin (Optional, for maintenance)
}