/**
  * Created by ESGI10601 on 13/06/2017.
  */
import com.sncf.fab.myfirstproject.parser.DataValidationTgaTgd
import com.sncf.fab.myfirstproject.pipelineData.TraitementTga
import com.sncf.fab.myfirstproject.utils.Conversion
import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.scalacheck.Prop.True
import org.scalatest.junit.JUnitRunner
import org.specs2.mutable.Specification
import org.specs2.execute.Pending


@RunWith(classOf[JUnitRunner])
class DataValidationTgaTgdTest extends Specification {

  /* ici on utilise les matchers de specs2 */
  s2"Vérifier la valeur attendue de la fonction Uppercase2 est true quand la chaine est majuscule $e1"
  s2"Vérifier la valeur attendue de la fonction Uppercase2 est false quand la chaine n'est pas en majuscule$e2"
  def e1 = DataValidationTgaTgd.isUpperCase2("Sam") must beEqualTo(false)
  def e2=  DataValidationTgaTgd.isUpperCase2("SAM") must beEqualTo(true)

}
