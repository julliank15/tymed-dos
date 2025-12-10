import scrapy

class PhotoItem(scrapy.Item):
    """Item for historical photo data"""
    
    # Basic information
    title = scrapy.Field()
    description = scrapy.Field()
    url = scrapy.Field()
    thumbnail_url = scrapy.Field()
    
    # Location information
    location_text = scrapy.Field()  # Raw location text from source
    latitude = scrapy.Field()
    longitude = scrapy.Field()
    address = scrapy.Field()  # Geocoded address
    
    # Date information
    date = scrapy.Field()  # ISO format date string
    
    # Metadata
    source = scrapy.Field()  # Source website/archive
    photographer = scrapy.Field()
    tags = scrapy.Field()  # List of tags
    
    # Processing flags
    geocoded = scrapy.Field()  # Boolean flag
    validated = scrapy.Field()  # Boolean flag
