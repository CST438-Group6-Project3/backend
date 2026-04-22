@RestController
@RequestMapping("/api/collections")
public class CollectionItemController {

    @Autowired
    private CollectionItemService service;

    // Add location to collection
    @PostMapping("/{collectionId}/items")
    public void addItem(@PathVariable Long collectionId,
                        @RequestParam Long locationId) {
        service.addItem(collectionId, locationId);
    }

    // Remove location
    @DeleteMapping("/{collectionId}/items/{locationId}")
    public void removeItem(@PathVariable Long collectionId,
                           @PathVariable Long locationId) {
        service.removeItem(collectionId, locationId);
    }
}