@Service
public class CollectionItemService {

    @Autowired
    private CollectionItemRepository repo;

    public void addItem(Long collectionId, Long locationId) {
        CollectionItem item = new CollectionItem();
        item.setCollectionId(collectionId);
        item.setLocationId(locationId);
        repo.save(item);
    }

    public void removeItem(Long collectionId, Long locationId) {
        repo.deleteByCollectionIdAndLocationId(collectionId, locationId);
    }
}