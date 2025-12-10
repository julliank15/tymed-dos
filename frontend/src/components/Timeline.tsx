import React from 'react';

interface TimelineProps {
  onDateSelect?: (date: Date) => void;
}

const Timeline: React.FC<TimelineProps> = () => {
  return (
    <div className="w-full bg-white shadow-lg rounded-lg p-4">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">Timeline</h2>
      <div className="flex items-center justify-center p-8 border-2 border-dashed border-gray-300 rounded">
        <p className="text-gray-500">Timeline component - displays photos chronologically</p>
      </div>
    </div>
  );
};

export default Timeline;
