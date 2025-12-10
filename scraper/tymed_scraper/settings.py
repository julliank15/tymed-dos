# Scrapy settings for tymed_scraper project

BOT_NAME = 'tymed_scraper'

SPIDER_MODULES = ['tymed_scraper.spiders']
NEWSPIDER_MODULE = 'tymed_scraper.spiders'

# Crawl responsibly by identifying yourself
USER_AGENT = 'tymed_scraper (+https://github.com/julliank15/tymed-dos)'

# Obey robots.txt rules
ROBOTSTXT_OBEY = True

# Configure maximum concurrent requests
CONCURRENT_REQUESTS = 16

# Configure a delay for requests (in seconds)
DOWNLOAD_DELAY = 1

# Disable cookies
COOKIES_ENABLED = False

# Enable or disable downloader middlewares
DOWNLOADER_MIDDLEWARES = {
    'scrapy.downloadermiddlewares.useragent.UserAgentMiddleware': None,
    'scrapy_user_agents.middlewares.RandomUserAgentMiddleware': 400,
}

# Configure item pipelines
ITEM_PIPELINES = {
    'tymed_scraper.pipelines.GeocodeLocationPipeline': 300,
    'tymed_scraper.pipelines.ValidatePhotoPipeline': 400,
}

# Set settings whose default value is deprecated to a future-proof value
REQUEST_FINGERPRINTER_IMPLEMENTATION = '2.7'
TWISTED_REACTOR = 'twisted.internet.asyncioreactor.AsyncioSelectorReactor'
FEED_EXPORT_ENCODING = 'utf-8'
