import logging
from datetime import datetime

logger = logging.getLogger(__name__)

class ValidatePhotoPipeline:
    """Pipeline to validate photo items"""
    
    def process_item(self, item, spider):
        """
        Validate that required fields are present and properly formatted
        """
        # Required fields
        required_fields = ['title', 'url', 'date']
        
        for field in required_fields:
            if not item.get(field):
                logger.error(f"Missing required field '{field}' in item: {item.get('title', 'Unknown')}")
                item['validated'] = False
                return item
        
        # Validate date format
        try:
            if isinstance(item['date'], str):
                datetime.fromisoformat(item['date'].replace('Z', '+00:00'))
        except ValueError as e:
            logger.error(f"Invalid date format for item {item['title']}: {str(e)}")
            item['validated'] = False
            return item
        
        # Validate coordinates if present
        if item.get('latitude') and item.get('longitude'):
            try:
                lat = float(item['latitude'])
                lng = float(item['longitude'])
                if not (-90 <= lat <= 90 and -180 <= lng <= 180):
                    logger.error(f"Invalid coordinates for item {item['title']}: ({lat}, {lng})")
                    item['validated'] = False
                    return item
            except (ValueError, TypeError) as e:
                logger.error(f"Invalid coordinate values for item {item['title']}: {str(e)}")
                item['validated'] = False
                return item
        
        item['validated'] = True
        logger.info(f"Validated item: {item['title']}")
        return item
