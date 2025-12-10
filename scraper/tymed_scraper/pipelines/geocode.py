from geopy.geocoders import Nominatim
from geopy.exc import GeocoderTimedOut, GeocoderServiceError
import logging

logger = logging.getLogger(__name__)

class GeocodeLocationPipeline:
    """Pipeline to geocode location text into coordinates"""
    
    def __init__(self):
        self.geocoder = Nominatim(user_agent="tymed_scraper")
    
    def process_item(self, item, spider):
        """
        Geocode the location_text field if coordinates are not provided
        """
        if item.get('latitude') and item.get('longitude'):
            item['geocoded'] = True
            return item
        
        if not item.get('location_text'):
            logger.warning(f"No location information for item: {item.get('title')}")
            item['geocoded'] = False
            return item
        
        try:
            location = self.geocoder.geocode(item['location_text'])
            if location:
                item['latitude'] = location.latitude
                item['longitude'] = location.longitude
                item['address'] = location.address
                item['geocoded'] = True
                logger.info(f"Geocoded: {item['location_text']} -> ({location.latitude}, {location.longitude})")
            else:
                logger.warning(f"Could not geocode: {item['location_text']}")
                item['geocoded'] = False
        except (GeocoderTimedOut, GeocoderServiceError) as e:
            logger.error(f"Geocoding error for {item['location_text']}: {str(e)}")
            item['geocoded'] = False
        
        return item
