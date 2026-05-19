// src/app/models/schedule.model.ts

export interface Schedule {
  id?: number;
  routeId: number;
  busId: number;
  departureTime: string;   // HH:mm
  arrivalTime: string;     // HH:mm
  scheduleDays: string;    // e.g. MON,TUE,WED
  isActive: boolean;
}
