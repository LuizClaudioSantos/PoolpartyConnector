package org.iadb.poolpartyconnector.thesauruscaching

import org.scalatest.{Suite,  BeforeAndAfterAll}

/**
 * Created by Daniel Maatari Okouya on 6/21/15.
 */
trait ThesaurusCacheServiceFixture extends BeforeAndAfterAll {this: Suite =>


  val cache: ThesaurusCacheService = ThesaurusCacheServiceModule.service

  override def beforeAll(): Unit = {
    super.beforeAll()
  }


  override def afterAll(): Unit = {

    ThesaurusCacheServiceModule.system.shutdown()
    super.afterAll()

  }

}