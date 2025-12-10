import React from 'react';

interface MapProps {
  onLocationSelect?: (lat: number, lng: number) => void;
}

const Map: React.FC<MapProps> = () => {
  // Note: Users will need to provide their own Mapbox token
  // const MAPBOX_TOKEN = import.meta.env.VITE_MAPBOX_TOKEN;

  return (
    <div className="w-full h-full bg-white shadow-lg rounded-lg overflow-hidden">
      <div className="p-4 bg-gray-800 text-white">
        <h2 className="text-2xl font-bold">Map View</h2>
        <p className="text-sm text-gray-300 mt-1">
          Interactive map with Mapbox GL JS
        </p>
      </div>
      <div className="h-[500px] flex items-center justify-center bg-gray-100">
        <div className="text-center p-8">
          <div className="mb-4">
            <svg className="mx-auto h-16 w-16 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-.553-.894L15 4m0 13V4m0 0L9 7" />
            </svg>
          </div>
          <p className="text-gray-700 font-semibold mb-2">Map Component</p>
          <p className="text-sm text-gray-500 max-w-md">
            This component will display an interactive Mapbox map with historical photo locations.
            To enable the map, install react-map-gl and add your Mapbox token to the .env file.
          </p>
          <div className="mt-4 text-xs text-gray-400 bg-gray-50 rounded p-3 max-w-md mx-auto">
            <code className="block">VITE_MAPBOX_TOKEN=your_token_here</code>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Map;
