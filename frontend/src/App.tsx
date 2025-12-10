import Timeline from './components/Timeline'
import Map from './components/Map'

function App() {
  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-blue-600 text-white shadow-lg">
        <div className="container mx-auto px-4 py-6">
          <h1 className="text-3xl font-bold">Tymed-Dos</h1>
          <p className="text-blue-100 mt-1">Historical Photo Archive & Visualization</p>
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Timeline Section */}
          <div className="space-y-6">
            <Timeline />
          </div>

          {/* Map Section */}
          <div className="space-y-6">
            <Map />
          </div>
        </div>

        {/* Info Section */}
        <div className="mt-8 bg-white rounded-lg shadow-lg p-6">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">Getting Started</h2>
          <div className="prose max-w-none">
            <p className="text-gray-600 mb-4">
              This is the Tymed-Dos starter project - a historical photo archival and visualization system.
            </p>
            <ul className="list-disc list-inside text-gray-600 space-y-2">
              <li>Frontend: React + Vite + Tailwind CSS + Mapbox GL JS</li>
              <li>Backend: FastAPI (Python) for REST/GraphQL API</li>
              <li>Scraper: Scrapy (Python) for data acquisition</li>
              <li>Database: PostgreSQL with PostGIS for geospatial queries</li>
            </ul>
            <div className="mt-4 p-4 bg-yellow-50 border-l-4 border-yellow-400">
              <p className="text-sm text-yellow-700">
                <strong>Note:</strong> To enable the interactive map, create a <code>.env</code> file 
                in the frontend directory and add your Mapbox token: <code>VITE_MAPBOX_TOKEN=your_token_here</code>
              </p>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-gray-800 text-white mt-12">
        <div className="container mx-auto px-4 py-6 text-center">
          <p className="text-gray-300">Tymed-Dos - Historical Photo Archive System</p>
        </div>
      </footer>
    </div>
  )
}

export default App
