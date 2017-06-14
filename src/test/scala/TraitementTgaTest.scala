/**
  * Created by ESGI10601 on 13/06/2017.
  */
import com.sncf.fab.myfirstproject.pipelineData.TraitementTga
import com.sncf.fab.myfirstproject.utils.Conversion
import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.specs2.mutable.Specification
import org.specs2.execute.Pending
@RunWith(classOf[JUnitRunner])
class TraitementTgaTest extends Specification {

  /* ici on utilise les matchers de specs2 */
  s2"Vérifier la valeur attendue de Refinery $e1"

  def e1 = (TraitementTga.getOutputRefineryPath()) must beEqualTo("refinery/ppiv/_TGA_20170713")

  s2"Vérifier la valeur attendue de Gold $e2"


  def e2 = (TraitementTga.getOutputGoldPath()) must beEqualTo("gold/ppiv/_TGA_"+ Conversion.getYearMonthDay(new DateTime()))

  s2"Vérifier la valeur attendue de la source $e3"

  def e3 = ( TraitementTga.getSource()) must beEqualTo("landing/ppiv/work/"+Conversion.getYearMonthDay(new DateTime())+"TGA.csv")


}
