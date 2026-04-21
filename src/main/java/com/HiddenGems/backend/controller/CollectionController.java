@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping
    public Collection createCollection(@RequestBody Collection collection) {
        return collectionService.create(collection);
    }

    @GetMapping
    public List<Collection> getUserCollections(@RequestParam Long userId) {
        return collectionService.getUserCollections(userId);
    }

    @PutMapping("/{id}")
    public Collection updateCollection(@PathVariable Long id,
                                       @RequestBody Collection updated) {
        return collectionService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable Long id) {
        collectionService.delete(id);
    }
}