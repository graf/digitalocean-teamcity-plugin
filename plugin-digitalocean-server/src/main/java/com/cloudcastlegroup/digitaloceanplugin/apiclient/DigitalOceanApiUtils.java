package com.cloudcastlegroup.digitaloceanplugin.apiclient;

import com.myjeeva.digitalocean.pojo.Droplet;
import org.jetbrains.annotations.NotNull;

/**
 * Created by graf on 31/08/16.
 */
public class DigitalOceanApiUtils {

  public static String getIpAddress(@NotNull final Droplet droplet) {
    if (droplet.getNetworks() != null
            && droplet.getNetworks().getVersion4Networks() != null
            && !droplet.getNetworks().getVersion4Networks().isEmpty()) {
      return droplet.getNetworks().getVersion4Networks().get(0).getIpAddress();
    } else {
      return null;
    }
  }
}
