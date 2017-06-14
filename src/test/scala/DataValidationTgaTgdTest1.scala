/**
  * Created by ESGI10601 on 13/06/2017.
  */
/**
  * Created by ESGI10601 on 13/06/2017.
  */
import com.sncf.fab.myfirstproject.parser.DataValidationTgaTgd
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner



@RunWith(classOf[JUnitRunner])
class DataValidationTgaTgdTest1 extends org.specs2.mutable.Specification {

  /* ici on utilise les matchers de specs2 */
  "Ici une autre variante de spécification" >> {
    "Premier cas doit être à True" >>{
      DataValidationTgaTgd.isUpperCase2("SAM") must_== true

    }
    "Deuxième cas doit être à False" >>{
      DataValidationTgaTgd.isUpperCase2("SAM") must_== false

    }

  }

}
