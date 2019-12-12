package com.bdeining.hbase.rest;

import static org.mockito.Mockito.mock;

import com.blade.mvc.RouteContext;
import org.junit.Before;
import org.junit.Test;

public class GetCapabilitiesHandlerTest {

  private GetCapabilitesHandler getCapabilitesHandler;

  @Before
  public void setUp() {
    getCapabilitesHandler = new GetCapabilitesHandler();
  }

  @Test
  public void testHandle() {
    getCapabilitesHandler.handle(mock(RouteContext.class));
  }
}
