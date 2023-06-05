import random

from django.http import JsonResponse

from kebabmania.models import Usuario, Opiniones, Kebab, Ciudad


def home(request):
    #Seguridad donde pilla el token del usuario y lo compara
    request_token = request.META.get('HTTP_SESSION_TOKEN')

    if request_token is None:
        return JsonResponse({"error": "Unauthorized"}, status=401)
    try:
        user = Usuario.objects.get(token=request_token)
    except Usuario.DoesNotExist:
        return JsonResponse({"error": "Unauthorized"}, status=401)
    #Si es distinto a GET sale del metodo con un error
    if request.method != "GET":
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)

    #Recoge el ultimo comentario en base al id y lo mete en un jsonObject, para el kebab usa un random.
    all_opinion = Opiniones.objects.filter(id_usuario=user)
    user_json = user.to_json()
    if len(all_opinion) == 0:
        id_lastop = 0
    else:
        id_lastop = Opiniones.objects.filter(id_usuario=user_json['id']).order_by("-id").first()


    if id_lastop != 0:
        id_lastop_json = id_lastop.to_json()
        last_opinion = Opiniones.objects.get(id=id_lastop_json['id'])

        json_last_opinion_json = last_opinion.to_json()
        kebab_nombre_opinion = Kebab.objects.get(id=json_last_opinion_json['id_kebab'])
        kebab_nombre_opinion_json = kebab_nombre_opinion.to_json()
        usuario_nombre_opinion = Usuario.objects.get(id=json_last_opinion_json['id_usuario'])
        usuario_nombre_opinion_json = usuario_nombre_opinion.to_json()


        json_opinion = {'nombreKebab': kebab_nombre_opinion_json['nombre'], 'nota': str (json_last_opinion_json['nota']), 'nombreUsuario': usuario_nombre_opinion_json['nombre']}



    else:
        json_opinion = {'nombreKebab': 'Unknown', 'nota': '0', 'nombreUsuario': 'Unknown'}

    json_response = []

    json_response.append(json_opinion)


    all_kebab = Kebab.objects.all()

    random_number = random.randint(1, len(all_kebab))


    random_kebab = Kebab.objects.get(id=random_number)

    random_kebab_json = random_kebab.to_json()

    ciudad = Ciudad.objects.get(id=random_kebab_json['id_ciudad'])
    ciudad_json = ciudad.to_json()
    random_kebab_json['nom_ciudad'] = ciudad_json['nom_ciudad']


    json_response.append(random_kebab_json)

    return JsonResponse(json_response, safe=False)
