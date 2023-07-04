import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/pet")
public class PetStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetStoreApplication.class, args);
    }

    @PostMapping
    public ResponseEntity<String> createPet(@RequestBody Pet pet) {

        PetService petService = new PetService();

        try {
            Pet createdPet = petService.createPet(pet);
            return ResponseEntity.ok("Pet created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create pet");
        }
    }

    @PutMapping("/{petId}")
    public ResponseEntity<String> updatePet(@PathVariable("petId") int petId, @RequestBody Pet pet) {

        PetService petService = new PetService();

        try {
            Pet existingPet = petService.getPetById(petId);

            if (existingPet != null) {
                existingPet.setName(pet.getName());
                existingPet.setDescription(pet.getDescription());

                Pet updatedPet = petService.updatePet(existingPet);

                return ResponseEntity.ok("Pet updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update pet");
        }
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPet(@PathVariable("petId") int petId) {

        PetService petService = new PetService();

        try {
            Pet pet = petService.getPetById(petId);

            if (pet != null) {
                return ResponseEntity.ok(pet);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
