package com.example.javaproject;

import com.example.javaproject.repository.PictureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaprojectApplication.class, args);
    }

    @Bean
    CommandLineRunner run(PictureRepository pictureRepository) {
        return args -> {
//            pictureRepository.addAuthor("John Singer Sargent", "John Singer Sargent, (born January 12, 1856, Florence, Italy—died April 15, 1925, London, England), Italian-born American painter whose elegant portraits provide an enduring image of Edwardian Age society.");
//            pictureRepository.addCategory("Impressionism", "Impressionism developed in France in the nineteenth century and is based on the practice of painting out of doors and spontaneously 'on the spot' rather than in a studio from sketches.");
//            pictureRepository.addPicture("Madame Gautreau Drinking a Toast", "John Singer Sargent", "Impressionism", 1883, "Was an early oil on panel study by Sargent that eventually resulted in the notorious Madame X painting.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\Madame_Gautreau_Drinking_a_Toast.jpg");
//            pictureRepository.addPicture("An Out of Doors Study", "John Singer Sargent", "Impressionism", 1889, "It refers to the painting we see being painted by the French painter Paul Helleu. But the title also applies to his own study with an Impressionist method of working “en Plein air” (painting outdoors).", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\An_Out-of-Doors_Study.jpg");
//            pictureRepository.addPicture("Lady and Child Asleep in a Punt under the Willows", "John Singer Sargent", "Impressionism", 1887, "In this work, the two sleepy figures under the shade of a willow tree are being lulled by the gentle movement of a barge in the heat of a summer afternoon.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\Lady_and_Child_Asleep_in_a_Punt_under_the_Willows.jpg");
//            pictureRepository.addCategory("Dutch Golden Age", "The Dutch Golden Age was a period in the history of the Netherlands, roughly spanning the era from 1588 to 1672, in which Dutch trade, science, and art and the Dutch military were among the most acclaimed in the world.");
//            pictureRepository.addAuthor("Rembrandt", "Dutch Baroque painter and printmaker, one of the greatest storytellers in the history of art, possessing an exceptional ability to render people in their various moods and dramatic guises.");
//            pictureRepository.addPicture("The Storm on the Sea of Galilee", "Rembrandt", "Dutch Golden Age", 1633, "It shows a close-up view of Christ's disciples struggling frantically against the heavy storm to regain control of their fishing boat. A huge wave beats the bow and rips the sail. One of the disciples is seen vomiting over the side. Another one, looking directly out at the viewer, is a self-portrait of the artist. Only Christ, depicted on the right, remains calm.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\The_Storm_on_the_Lake_of_Galilee.jpg");
//            pictureRepository.addPicture("Anatomy lesson by Dr. Nicolaes Tulp", "Rembrandt", "Dutch Golden Age", 1632, "Rembrandt portrayed the surgeons in action, and they are all looking at different things. Dynamism is added to the scene by the great contrasts between light and dark. In this group portrait, the young painter displayed his legendary technique and his great talent for painting lifelike portraits.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\The_Anatomy_lesson_of_Dr_Tulp.jpg");
//            pictureRepository.addAuthor("Jan Asselijn", "Dutch painter of French descent, active mainly in Amsterdam. He spent several years in Italy in the late 1630s and early 1640s and came to specialize in real and imaginary scenes of the Roman Campagna.");
//            pictureRepository.addPicture("The Threatened Swan", "Jan Asselijn", "Dutch Golden Age", 1650, "It portrays a swan aggressively defending its nest, became a symbol of Dutch national resistance, although it is unknown if Asselijn intended it to be so. In particular, it was interpreted as a depiction of Johan de Witt.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\The_Threatened_Swan.jpg");
//            pictureRepository.addAuthor("Gustav Klimt", "Was an Austrian symbolist painter and one of the most prominent members of the Vienna Secession movement. Klimt is noted for his paintings, murals, sketches, and other objets d'art.");
//            pictureRepository.addCategory("Symbolism", "Was a late 19th-century art movement of French and Belgian origin in poetry and other arts seeking to represent absolute truths symbolically through language and metaphorical images, mainly as a reaction against naturalism and realism.");
//            pictureRepository.addPicture("Death and Life", "Gustav Klimt", "Symbolism", 1910, "Gustav Klimt’s large painting Death and Life, created in 1910, features not a personal death but rather merely an allegorical Grim Reaper who gazes at “life” with a malicious grin.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\death_and_life.jpg");
//            pictureRepository.addPicture("Avenue to Schloss Kammer", "Gustav Klimt", "Symbolism", 1912, "In this picture, he had decided to paint a view of the long avenue of trees leading to the castle entrance. Through foreshortening, the rows of trees on either side greatly draw the viewer in and create depth.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\Avenue_of_Schloss_Kammer_Park.jpg");
//            pictureRepository.addPicture("Flowering Poppies", "Gustav Klimt", "Symbolism", 1907, "In this work, a meadow rich with magnificent red poppies extends across almost the entire surface of the painting.", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\Field_of_Poppies.jpg");
//            pictureRepository.addCategory("Postimpressionism", "Emerged as a reaction against Impressionists' concern for the naturalistic depiction of light and colour. Its broad emphasis on abstract qualities or symbolic content means Post-Impressionism encompasses Les Nabis, Neo-Impressionism, Symbolism, Cloisonnism, the Pont-Aven School, and Synthetism, along with some later Impressionists' work. The movement's principal artists were Paul Cézanne (known as the father of Post-Impressionism), Paul Gauguin, Vincent van Gogh and Georges Seurat.");
//            pictureRepository.addAuthor("Paul Cezanne", "was a French artist and Post-Impressionist painter whose work laid the foundations of the transition from the 19th-century conception of artistic endeavour to a new and radically different world of art in the 20th century.");
//            pictureRepository.addPicture("Still life with kettle", "Paul Cezanne", "Postimpressionism", 1867, "", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\Still_life_with_kettle.jpg");

//            pictureRepository.addPicture("", "Paul Cezanne", "Postimpressionism", , "", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\");
//            pictureRepository.addPicture("", "Paul Cezanne", "Postimpressionism", , "", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\");
//            pictureRepository.addPicture("", "Paul Cezanne", "Postimpressionism", , "", "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\");


        };
    }
}
