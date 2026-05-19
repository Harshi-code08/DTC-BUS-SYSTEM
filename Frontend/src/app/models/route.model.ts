// src/app/models/route.model.ts

export interface Route {
  id?: number;
  routeNumber: string;
  routeName: string;
  sourceStop: string;
  destinationStop: string;
  totalDistanceKm: number;
  fare: number;
  totalStops: number;
  stops: string;       // comma-separated
  isActive: boolean;
}
