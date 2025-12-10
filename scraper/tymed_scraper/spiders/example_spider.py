import scrapy
from tymed_scraper.items import PhotoItem
from datetime import datetime

class ExampleSpider(scrapy.Spider):
    """
    Example spider template for scraping historical photo archives.
    
    Customize this spider for specific photo archive websites.
    """
    name = 'example'
    allowed_domains = ['example.com']
    start_urls = ['https://example.com/photos']
    
    def parse(self, response):
        """
        Parse the main page and extract photo links
        """
        # Example: Extract photo links
        photo_links = response.css('a.photo-link::attr(href)').getall()
        
        for link in photo_links:
            yield response.follow(link, callback=self.parse_photo)
        
        # Example: Follow pagination
        next_page = response.css('a.next-page::attr(href)').get()
        if next_page:
            yield response.follow(next_page, callback=self.parse)
    
    def parse_photo(self, response):
        """
        Parse individual photo pages and extract data
        """
        item = PhotoItem()
        
        # Extract basic information
        item['title'] = response.css('h1.photo-title::text').get()
        item['description'] = response.css('p.photo-description::text').get()
        item['url'] = response.css('img.photo-main::attr(src)').get()
        item['thumbnail_url'] = response.css('img.photo-thumbnail::attr(src)').get()
        
        # Extract location
        item['location_text'] = response.css('span.photo-location::text').get()
        
        # Extract date
        date_str = response.css('span.photo-date::text').get()
        if date_str:
            try:
                # Parse date and convert to ISO format
                date_obj = datetime.strptime(date_str, '%Y-%m-%d')  # Adjust format as needed
                item['date'] = date_obj.isoformat()
            except ValueError:
                self.logger.warning(f"Could not parse date: {date_str}")
                item['date'] = None
        
        # Extract metadata
        item['source'] = self.allowed_domains[0]
        item['photographer'] = response.css('span.photographer::text').get()
        item['tags'] = response.css('a.tag::text').getall()
        
        yield item
