package org.iadb.poolpartyconnector.dspacextension.dspaceconnectorconfiguration

import java.net.URL
import com.typesafe.config.ConfigFactory
import org.iadb.poolpartyconnector.connectorconfiguration.PoolpartySettings
import scala.collection.JavaConverters._



/**
 * Created by Daniel Maatari Okouya on 7/8/15.
 *
 */
trait DspacePoolPartyConnectorSettings {

  def poolpartyServerSettings   : PoolpartySettings
  def fieldsSettingsList        : List[FieldSettings]
  def fieldsSettingsMap         : Map[String, FieldSettings]
  def skoslangCodesMappingsList : List[SkoslangCodeMapping]
  def skoslangCodesMappingsMap  : Map[String, SkoslangCodeMapping]
}


case class DspaceDspacePoolPartyConnectorSettingImpl (configUri: String) extends DspacePoolPartyConnectorSettings {


  private val config                           = ConfigFactory.parseURL(new URL(configUri)).resolve()
  private val configFieldSettingslist          = config.getConfigList("PoolPartyConnectorSettings.FieldSettings").asScala.toList
  private val configPoolPartySettings          = config.getConfig("PoolPartyConnectorSettings.PoolPartySettings")
  private val configSkosLangCodesMappingsList  = config.getConfigList("PoolPartyConnectorSettings.skoslangcodesmappings").asScala.toList


  val poolpartyServerSettings            = PoolpartySettings( configPoolPartySettings.getString("apirootEndpoint"),
                                                              configPoolPartySettings.getString("thesaurusapiEndpoint"),
                                                              configPoolPartySettings.getString("extratorapiEndpoint"),
                                                              configPoolPartySettings.getString("coreProjectId"),
                                                              configPoolPartySettings.getString("coreThesaurusUri"),
                                                              configPoolPartySettings.getString("jelProjectId"),
                                                              configPoolPartySettings.getString("jelThesaurusUri"))


  val fieldsSettingsList                 = configFieldSettingslist map { e =>
                                                      FieldSettings(e.getString("fieldname"), e.getBoolean("treeBrowser"),
                                                      e.getBoolean("multilanguage"), e.getStringList("languages").asScala.toList,
                                                      e.getBoolean("closed"), e.getString("scheme"), e.getString("poolpartyProjectId")) }

  val fieldsSettingsMap                  = Map(fieldsSettingsList map {e => (e.fieldName, e)}: _*)




  val skoslangCodesMappingsList          = configSkosLangCodesMappingsList map {e => SkoslangCodeMapping(e.getString("language"), e.getString("skoscode")) }



  val skoslangCodesMappingsMap           = Map(skoslangCodesMappingsList map {e => (e.lang, e)}: _*)

}