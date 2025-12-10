# Tymed-Dos Scraper

Scrapy-based web scraper for acquiring historical photos from various online archives.

## Features

- Modular spider architecture for different photo sources
- Automatic geocoding of location information
- Data validation pipeline
- Integration with GeoPy for location processing
- Pandas support for data transformation

## Setup

1. Create a virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. Install dependencies:
```bash
pip install -r requirements.txt
```

## Usage

### Running a Spider

```bash
# Run the example spider
scrapy crawl example -o output.json

# Run with custom settings
scrapy crawl example -o output.json -s DOWNLOAD_DELAY=2
```

### Creating a New Spider

1. Create a new spider file in `tymed_scraper/spiders/`
2. Inherit from `scrapy.Spider`
3. Define the spider's name, allowed domains, and start URLs
4. Implement the `parse` method to extract data

Example:
```python
import scrapy
from tymed_scraper.items import PhotoItem

class MyArchiveSpider(scrapy.Spider):
    name = 'myarchive'
    allowed_domains = ['myarchive.com']
    start_urls = ['https://myarchive.com/photos']
    
    def parse(self, response):
        # Your scraping logic here
        item = PhotoItem()
        item['title'] = response.css('h1::text').get()
        # ... extract more fields
        yield item
```

## Data Pipeline

The scraper uses the following pipelines to process data:

1. **GeocodeLocationPipeline** (priority: 300)
   - Converts location text to coordinates using GeoPy
   - Adds geocoded address information

2. **ValidatePhotoPipeline** (priority: 400)
   - Validates required fields
   - Checks date format
   - Validates coordinate ranges

## Photo Item Schema

Each scraped photo contains:

```python
{
    'title': str,              # Photo title
    'description': str,        # Photo description
    'url': str,                # Full-size image URL
    'thumbnail_url': str,      # Thumbnail URL
    'location_text': str,      # Raw location text
    'latitude': float,         # Latitude coordinate
    'longitude': float,        # Longitude coordinate
    'address': str,            # Geocoded address
    'date': str,               # ISO format date
    'source': str,             # Source website
    'photographer': str,       # Photographer name
    'tags': list,              # List of tags
    'geocoded': bool,          # Geocoding success flag
    'validated': bool          # Validation success flag
}
```

## Output Formats

Scrapy supports multiple output formats:

```bash
# JSON
scrapy crawl example -o output.json

# JSON Lines
scrapy crawl example -o output.jsonl

# CSV
scrapy crawl example -o output.csv

# XML
scrapy crawl example -o output.xml
```

## Project Structure

```
scraper/
├── tymed_scraper/
│   ├── __init__.py
│   ├── items.py              # Item definitions
│   ├── settings.py           # Scrapy settings
│   ├── pipelines/            # Data processing pipelines
│   │   ├── __init__.py
│   │   ├── geocode.py
│   │   └── validate.py
│   └── spiders/              # Spider implementations
│       ├── __init__.py
│       └── example_spider.py
├── scrapy.cfg
├── requirements.txt
└── README.md
```

## Best Practices

1. **Respect robots.txt**: Always obey site rules
2. **Use delays**: Set appropriate `DOWNLOAD_DELAY` to avoid overwhelming servers
3. **User agent**: Identify your bot with a descriptive user agent
4. **Error handling**: Handle missing data gracefully
5. **Logging**: Use Scrapy's logging system for debugging

## Troubleshooting

### Geocoding Errors

If geocoding fails frequently:
- Check your internet connection
- Consider using a paid geocoding service for higher limits
- Implement caching for repeated location lookups

### Rate Limiting

If you encounter rate limiting:
- Increase `DOWNLOAD_DELAY` in settings
- Reduce `CONCURRENT_REQUESTS`
- Use `AutoThrottle` extension

## Next Steps

1. Implement spiders for specific photo archives
2. Add database integration for direct data storage
3. Implement image downloading and processing
4. Add retry logic for failed geocoding
5. Create data quality reports
