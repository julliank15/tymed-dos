export interface Photo {
  id: string;
  title: string;
  description?: string;
  url: string;
  thumbnailUrl?: string;
  location: {
    latitude: number;
    longitude: number;
    address?: string;
  };
  date: Date;
  metadata?: {
    source?: string;
    photographer?: string;
    tags?: string[];
  };
}

export interface PhotoFilter {
  startDate?: Date;
  endDate?: Date;
  location?: {
    latitude: number;
    longitude: number;
    radius: number;
  };
  tags?: string[];
}
