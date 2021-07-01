/*
 * Copyright © Zurich Foremost Services Limited 2019
 * All Rights Reserved
 *
 * Zurich Foremost NSS programme.
 *
 * Created:  Sep 12, 2019 9:59:39 AM
 * Author:   a.paramasivamchellam
 * Project:  nssWeb
 *
 * $LastChangedBy$
 * $LastChangedRevision$
 * $LastChangedDate$
 */
package provider;

import java.lang.reflect.Method;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.sun.faces.spi.DiscoverableInjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.vendor.WebContainerInjectionProvider;

/**
 *
 */
public class WebSphereInjectionProvider extends DiscoverableInjectionProvider {
    
    private Object annotationHelper;
    private WebContainerInjectionProvider provder;

    public WebSphereInjectionProvider() throws Exception {
        Method annotationHelperManagerGetInstanceMethod = Class.forName(
                "com.ibm.wsspi.webcontainer.annotation.AnnotationHelperManager").getDeclaredMethod("getInstance",
                ServletContext.class);

        Object annotationHelperManager = annotationHelperManagerGetInstanceMethod.invoke(null,
                (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());

        annotationHelper = annotationHelperManager.getClass().getDeclaredMethod("getAnnotationHelper")
                .invoke(annotationHelperManager);
        annotationHelper.getClass().getMethod("inject", Object.class);

        provder = new WebContainerInjectionProvider();
    }

    @Override
    public void inject(Object paramObject) throws InjectionProviderException {
        try {
            annotationHelper.getClass().getMethod("inject", Object.class).invoke(annotationHelper, paramObject);
        } catch (Exception e) {
            throw new InjectionProviderException(e);
        }
    }

    @Override
    public void invokePreDestroy(Object paramObject) throws InjectionProviderException {
        provder.invokePreDestroy(paramObject);
    }

    @Override
    public void invokePostConstruct(Object paramObject) throws InjectionProviderException {
        provder.invokePostConstruct(paramObject);
    }

}