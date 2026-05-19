// src/app/models/bus.model.ts

export interface Bus {
  id?: number;
  busNumber: string;
  busType: 'AC' | 'NON_AC' | 'ELECTRIC';
  capacity: number;
  driverName: string;
  driverContact: string;
  isActive: boolean;
}
