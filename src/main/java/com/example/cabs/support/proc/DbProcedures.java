package com.example.cabs.support.proc;

/** Auto-generated from cabs_db_scripts.sql. Edit by regeneration. */
public final class DbProcedures {
  public static final class DABS_sp_ActivateUser {
    public static final String NAME = "DABS_sp_ActivateUser";
    public static final class P {
      public static final String p_UserId = "p_UserId";
      public static final String p_IsActive = "p_IsActive";
    }
  }
  public static final class DABS_sp_AdminCancelAppointment {
    public static final String NAME = "DABS_sp_AdminCancelAppointment";
    public static final class P {
      public static final String p_ApptId = "p_ApptId";
      public static final String p_AdminUserId = "p_AdminUserId";
      public static final String p_Reason = "p_Reason";
    }
  }
  public static final class DABS_sp_AdminGenerateSlots {
    public static final String NAME = "DABS_sp_AdminGenerateSlots";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_WorkDate = "p_WorkDate";
      public static final String p_StartHour = "p_StartHour";
      public static final String p_EndHour = "p_EndHour";
      public static final String p_AdminUserId = "p_AdminUserId";
    }
  }
  public static final class DABS_sp_AdminGenerateSlotsRange {
    public static final String NAME = "DABS_sp_AdminGenerateSlotsRange";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_FromDate = "p_FromDate";
      public static final String p_ToDate = "p_ToDate";
      public static final String p_StartHour = "p_StartHour";
      public static final String p_EndHour = "p_EndHour";
      public static final String p_AdminUserId = "p_AdminUserId";
    }
  }
  public static final class DABS_sp_BookAppointment {
    public static final String NAME = "DABS_sp_BookAppointment";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_PatientId = "p_PatientId";
      public static final String p_StartUtc = "p_StartUtc";
    }
  }
  public static final class DABS_sp_CancelAppointment {
    public static final String NAME = "DABS_sp_CancelAppointment";
    public static final class P {
      public static final String p_ApptId = "p_ApptId";
      public static final String p_PatientId = "p_PatientId";
      public static final String p_ByUserId = "p_ByUserId";
    }
  }
  public static final class DABS_sp_ClearSlotsRange {
    public static final String NAME = "DABS_sp_ClearSlotsRange";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_FromDate = "p_FromDate";
      public static final String p_ToDate = "p_ToDate";
    }
  }
  public static final class DABS_sp_CreateDoctor {
    public static final String NAME = "DABS_sp_CreateDoctor";
    public static final class P {
      public static final String p_Email = "p_Email";
    }
  }
  public static final class DABS_sp_DeleteDoctorSoft {
    public static final String NAME = "DABS_sp_DeleteDoctorSoft";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
    }
  }
  public static final class DABS_sp_DeletePatientSoft {
    public static final String NAME = "DABS_sp_DeletePatientSoft";
    public static final class P {
      public static final String p_PatientId = "p_PatientId";
    }
  }
  public static final class DABS_sp_DoctorGenerateSlots {
    public static final String NAME = "DABS_sp_DoctorGenerateSlots";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_WorkDate = "p_WorkDate";
      public static final String p_StartHour = "p_StartHour";
      public static final String p_EndHour = "p_EndHour";
      public static final String p_ByUserId = "p_ByUserId";
      public static final String p_Source = "p_Source";
    }
  }
  public static final class DABS_sp_GetDoctorIdByUserId {
    public static final String NAME = "DABS_sp_GetDoctorIdByUserId";
    public static final class P {
      public static final String p_UserId = "p_UserId";
    }
  }
  public static final class DABS_sp_GetPatientById {
    public static final String NAME = "DABS_sp_GetPatientById";
    public static final class P {
      public static final String p_PatientId = "p_PatientId";
    }
  }
  public static final class DABS_sp_GetPatientIdByUserId {
    public static final String NAME = "DABS_sp_GetPatientIdByUserId";
    public static final class P {
      public static final String p_UserId = "p_UserId";
    }
  }
  public static final class DABS_sp_GetUserSaltByEmail {
    public static final String NAME = "DABS_sp_GetUserSaltByEmail";
    public static final class P {
      public static final String p_Email = "p_Email";
    }
  }
  public static final class DABS_sp_ListAppointments {
    public static final String NAME = "DABS_sp_ListAppointments";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_PatientId = "p_PatientId";
      public static final String p_FromUtc = "p_FromUtc";
    }
  }
  public static final class DABS_sp_ListAppointmentsByDoctor {
    public static final String NAME = "DABS_sp_ListAppointmentsByDoctor";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_FromUtc = "p_FromUtc";
    }
  }
  public static final class DABS_sp_ListAppointmentsByPatient {
    public static final String NAME = "DABS_sp_ListAppointmentsByPatient";
    public static final class P {
      public static final String p_PatientId = "p_PatientId";
      public static final String p_FromUtc = "p_FromUtc";
    }
  }
  public static final class DABS_sp_ListAvailableSlots {
    public static final String NAME = "DABS_sp_ListAvailableSlots";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_WorkDate = "p_WorkDate";
    }
  }
  public static final class DABS_sp_ListDoctorsBasic {
    public static final String NAME = "DABS_sp_ListDoctorsBasic";
  }
  public static final class DABS_sp_ListPatientsPendingActivation {
    public static final String NAME = "DABS_sp_ListPatientsPendingActivation";
  }
  public static final class DABS_sp_Login {
    public static final String NAME = "DABS_sp_Login";
    public static final class P {
      public static final String p_Email = "p_Email";
    }
  }
  public static final class DABS_sp_Notifications_CountUnread {
    public static final String NAME = "DABS_sp_Notifications_CountUnread";
    public static final class P {
      public static final String p_UserId = "p_UserId";
    }
  }
  public static final class DABS_sp_Notifications_Delete {
    public static final String NAME = "DABS_sp_Notifications_Delete";
    public static final class P {
      public static final String p_UserId = "p_UserId";
      public static final String p_NotificationId = "p_NotificationId";
    }
  }
  public static final class DABS_sp_Notifications_ListForUser {
    public static final String NAME = "DABS_sp_Notifications_ListForUser";
    public static final class P {
      public static final String p_UserId = "p_UserId";
      public static final String p_OnlyUnread = "p_OnlyUnread";
      public static final String p_Top = "p_Top";
    }
  }
  public static final class DABS_sp_Notifications_MarkAll {
    public static final String NAME = "DABS_sp_Notifications_MarkAll";
    public static final class P {
      public static final String p_UserId = "p_UserId";
    }
  }
  public static final class DABS_sp_Notifications_MarkRead {
    public static final String NAME = "DABS_sp_Notifications_MarkRead";
    public static final class P {
      public static final String p_UserId = "p_UserId";
      public static final String p_NotificationId = "p_NotificationId";
    }
  }
  public static final class DABS_sp_Notify_Create {
    public static final String NAME = "DABS_sp_Notify_Create";
    public static final class P {
      public static final String p_Type = "p_Type";
    }
  }
  public static final class DABS_sp_RegisterPatient {
    public static final String NAME = "DABS_sp_RegisterPatient";
    public static final class P {
      public static final String p_Email = "p_Email";
    }
  }
  public static final class DABS_sp_Report_DoctorAppointments {
    public static final String NAME = "DABS_sp_Report_DoctorAppointments";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_FromUtc = "p_FromUtc";
    }
  }
  public static final class DABS_sp_Report_DoctorAppointments_Csv {
    public static final String NAME = "DABS_sp_Report_DoctorAppointments_Csv";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_FromUtc = "p_FromUtc";
    }
  }
  public static final class DABS_sp_Report_DoctorTotals {
    public static final String NAME = "DABS_sp_Report_DoctorTotals";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_FromUtc = "p_FromUtc";
    }
  }
  public static final class DABS_sp_UpdateDoctor {
    public static final String NAME = "DABS_sp_UpdateDoctor";
    public static final class P {
      public static final String p_DoctorId = "p_DoctorId";
      public static final String p_Name = "p_Name";
    }
  }
  public static final class DABS_sp_UpdatePatient {
    public static final String NAME = "DABS_sp_UpdatePatient";
    public static final class P {
      public static final String p_PatientId = "p_PatientId";
      public static final String p_FullName = "p_FullName";
    }
  }
}